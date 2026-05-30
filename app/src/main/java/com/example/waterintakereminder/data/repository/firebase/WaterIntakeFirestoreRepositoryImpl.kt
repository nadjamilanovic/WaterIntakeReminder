package com.example.waterintakereminder.data.repository.firebase

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import com.google.firebase.firestore.FirebaseFirestore
import com.example.waterintakereminder.data.datasource.network.dto.WaterIntakeFirestoreDto
import com.example.waterintakereminder.data.datasource.network.dto.toWaterIntake
import com.example.waterintakereminder.data.model.WaterIntake
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WaterIntakeFirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : WaterIntakeFirestoreRepository {

    private fun getCollection(userId: String) = firestore.collection("users")
        .document(userId)
        .collection("water_intakes")

    override fun observeWaterIntakes(userId: String): Flow<List<WaterIntake>> = callbackFlow {
        val subscription = getCollection(userId)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val intakes = snapshot?.documents?.mapNotNull { doc ->
                    val dto = doc.toObject(WaterIntakeFirestoreDto::class.java)
                    dto?.toWaterIntake()
                } ?: emptyList()
                trySend(intakes)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun getWaterIntakes(userId: String): Result<List<WaterIntake>> {
        return try {
            val snapshot = getCollection(userId).get().await()
            val intakes = snapshot.documents.mapNotNull { doc ->
                val dto = doc.toObject(WaterIntakeFirestoreDto::class.java)
                dto?.toWaterIntake()
            }
            Result.success(intakes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addWaterIntake(amountMl: Int, time: String, userId: String): Result<Unit> {
        return try {
            val dto = WaterIntakeFirestoreDto(
                amountMl = amountMl,
                time = time,
                isDrank = true,
                timestamp = System.currentTimeMillis()
            )
            getCollection(userId).add(dto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleWaterIntake(intakeId: String, isDrank: Boolean, userId: String): Result<Unit> {
        return try {
            getCollection(userId).document(intakeId).update("isDrank", isDrank).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteWaterIntake(intakeId: String, userId: String): Result<Unit> {
        return try {
            getCollection(userId).document(intakeId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun exportWaterIntakes(intakes: List<WaterIntake>): Result<String> = withContext(Dispatchers.IO) {
        try {
            val csvContent = buildString {
                appendLine("id,amount_ml,time,is_drank")
                intakes.forEach { intake ->
                    appendLine("${intake.id},${intake.amountMl},${intake.time},${intake.isDrank}")
                }
            }

            val fileName = "water_intakes_export_${System.currentTimeMillis()}.csv"

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                ?: return@withContext Result.failure(Exception("Failed to create file"))

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(csvContent.toByteArray())
            } ?: return@withContext Result.failure(Exception("Failed to open output stream"))

            Result.success(uri.toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
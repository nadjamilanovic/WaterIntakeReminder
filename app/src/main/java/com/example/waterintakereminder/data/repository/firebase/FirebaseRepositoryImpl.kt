package com.example.waterintakereminder.data.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.example.waterintakereminder.data.datasource.network.dto.FirestoreWaterIntakeDto
import com.example.waterintakereminder.data.datasource.network.dto.toWaterIntake
import com.example.waterintakereminder.data.model.WaterIntake
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreRepository {

    private val intakesCollection = firestore.collection("water_intakes")

    override suspend fun saveWaterIntake(intake: WaterIntake, userId: String): Result<Unit> {
        return try {
            val docId = intakesCollection.document().id
            val dto = FirestoreWaterIntakeDto(
                id = docId,
                amountMl = intake.amountMl,
                time = intake.time,
                isDrank = intake.isDrank,
                userId = userId
            )
            intakesCollection.document(docId).set(dto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWaterIntakes(userId: String): Result<List<WaterIntake>> {
        return try {
            val snapshot = intakesCollection
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            val intakes = snapshot.documents.mapNotNull { doc ->
                val dto = doc.toObject(FirestoreWaterIntakeDto::class.java)
                dto?.toWaterIntake()
            }
            Result.success(intakes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeWaterIntakes(userId: String): Flow<List<WaterIntake>> = callbackFlow {
        val subscription = intakesCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val intakes = snapshot?.documents?.mapNotNull { doc ->
                    val dto = doc.toObject(FirestoreWaterIntakeDto::class.java)
                    dto?.toWaterIntake()
                } ?: emptyList()
                trySend(intakes)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun updateWaterIntake(intake: WaterIntake, userId: String): Result<Unit> {
        return try {
            val data = mapOf(
                "amountMl" to intake.amountMl,
                "time" to intake.time,
                "isDrank" to intake.isDrank
            )
            intakesCollection.document(intake.id.toString()).update(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteWaterIntake(intakeId: String, userId: String): Result<Unit> {
        return try {
            intakesCollection.document(intakeId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
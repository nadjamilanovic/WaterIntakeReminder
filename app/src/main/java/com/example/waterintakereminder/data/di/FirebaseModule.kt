package com.example.waterintakereminder.data.di

import com.example.waterintakereminder.data.repository.firebase.AuthRepository
import com.example.waterintakereminder.data.repository.firebase.AuthRepositoryImpl
import com.example.waterintakereminder.data.repository.firebase.FirestoreRepository
import com.example.waterintakereminder.data.repository.firebase.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }
    @Provides
    @Singleton
    fun provideFirestoreRepository(firestore: FirebaseFirestore): FirestoreRepository {
        return FirestoreRepositoryImpl(firestore)
    }
}
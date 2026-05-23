package com.example.waterintakereminder.data.di

import com.example.waterintakereminder.data.datasource.network.service.WaterIntakeApiService
import com.example.waterintakereminder.data.repository.cloud.CloudWaterIntakeRepository
import com.example.waterintakereminder.data.repository.cloud.WaterIntakeCloudRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.0.2.2:8000/"

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWaterIntakeApiService(retrofit: Retrofit): WaterIntakeApiService {
        return retrofit.create(WaterIntakeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWaterIntakeCloudRepository(
        apiService: WaterIntakeApiService
    ): WaterIntakeCloudRepository {
        return CloudWaterIntakeRepository(apiService)
    }
}
package com.example.financial_control.domain.module

import android.content.Context
import com.example.financial_control.domain.client.GoogleAuthClient
import com.example.financial_control.domain.repository.AuthRepository
import com.example.financial_control.domain.repository.AuthRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleAuthClient(
        @ApplicationContext context: Context
    ): GoogleAuthClient {
        return GoogleAuthClient(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepository: AuthRepository
    ): AuthRepositoryInterface
}
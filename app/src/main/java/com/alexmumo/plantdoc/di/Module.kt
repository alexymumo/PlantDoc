package com.alexmumo.plantdoc.di

import android.content.Context
import com.alexmumo.plantdoc.data.repository.AuthRepository
import com.alexmumo.plantdoc.data.repository.AuthRepositoryImpl
import com.alexmumo.plantdoc.data.repository.UserRepository
import com.alexmumo.plantdoc.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun providesAuthRepositoyImpl(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Singleton
    @Provides
    fun providesUserRepositoryImpl(): UserRepository {
        return UserRepositoryImpl()
    }
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providesDispatcher() = Dispatchers.Main as CoroutineDispatcher
}



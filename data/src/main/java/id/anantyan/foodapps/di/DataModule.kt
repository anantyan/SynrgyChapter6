package id.anantyan.foodapps.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.data.local.dao.FoodsDao
import id.anantyan.foodapps.data.local.dao.UsersDao
import id.anantyan.foodapps.data.local.datasource.UploadLocalDataSource
import id.anantyan.foodapps.data.local.datasource.UploadLocalDataSourceImpl
import id.anantyan.foodapps.data.local.repository.FoodsLocalRepositoryImpl
import id.anantyan.foodapps.data.local.repository.PreferencesRepositoryImpl
import id.anantyan.foodapps.data.local.repository.UserRepositoryImpl
import id.anantyan.foodapps.data.remote.datasource.UploadRemoteDataSource
import id.anantyan.foodapps.data.remote.datasource.UploadRemoteDataSourceImpl
import id.anantyan.foodapps.data.remote.repository.FoodsRemoteRepositoryImpl
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.data.remote.service.UploadApi
import id.anantyan.foodapps.data.repository.UsersRepositoryImpl
import id.anantyan.foodapps.domain.repository.FoodsLocalRepository
import id.anantyan.foodapps.domain.repository.FoodsRemoteRepository
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesRepository
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserRepository
import id.anantyan.foodapps.domain.repository.UserUseCase
import id.anantyan.foodapps.domain.repository.UsersRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFoodsRemoteRepositoryImpl(@Named("RECIPE") foodsApi: FoodsApi): FoodsRemoteRepository {
        return FoodsRemoteRepositoryImpl(foodsApi)
    }

    @Singleton
    @Provides
    fun provideFoodsLocalRepositoryImpl(foodsDao: FoodsDao): FoodsLocalRepository {
        return FoodsLocalRepositoryImpl(foodsDao)
    }

    @Singleton
    @Provides
    fun providePreferencesRepositoryImpl(@ApplicationContext context: Context): PreferencesRepository {
        return PreferencesRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideUserRepositoryImpl(usersDao: UsersDao): UserRepository {
        return UserRepositoryImpl(usersDao)
    }

    @Singleton
    @Provides
    fun provideUploadLocalDataSourceImpl(usersDao: UsersDao): UploadLocalDataSource {
        return UploadLocalDataSourceImpl(usersDao)
    }

    @Singleton
    @Provides
    fun provideUploadRemoteDataSourceImpl(@Named("UPLOAD") uploadApi: UploadApi): UploadRemoteDataSource {
        return UploadRemoteDataSourceImpl(uploadApi)
    }

    @Singleton
    @Provides
    fun provideUsersRepositoryImpl(
        uploadLocalDataSource: UploadLocalDataSource,
        uploadRemoteDataSource: UploadRemoteDataSource
    ): UsersRepository {
        return UsersRepositoryImpl(
            uploadLocalDataSource,
            uploadRemoteDataSource
        )
    }
}
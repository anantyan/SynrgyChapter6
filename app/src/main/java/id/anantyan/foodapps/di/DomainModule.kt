package id.anantyan.foodapps.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.data.local.dao.FoodsDao
import id.anantyan.foodapps.data.local.dao.UsersDao
import id.anantyan.foodapps.data.local.repository.FoodsLocalRepositoryImpl
import id.anantyan.foodapps.data.local.repository.PreferencesRepositoryImpl
import id.anantyan.foodapps.data.local.repository.UserRepositoryImpl
import id.anantyan.foodapps.data.remote.repository.FoodsRemoteRepositoryImpl
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.domain.repository.FoodsLocalRepository
import id.anantyan.foodapps.domain.repository.FoodsRemoteRepository
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesRepository
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserRepository
import id.anantyan.foodapps.domain.repository.UserUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideFoodsRemoteRepositoryImpl(foodsApi: FoodsApi): FoodsRemoteRepository {
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
    fun provideUsersRepositoryImpl(usersDao: UsersDao): UserRepository {
        return UserRepositoryImpl(usersDao)
    }

    @Singleton
    @Provides
    fun provideFoodsRepositoryUseCase(
        foodsRemoteRepository: FoodsRemoteRepository,
        foodsLocalRepository: FoodsLocalRepository
    ): FoodsUseCase {
        return FoodsUseCase(foodsRemoteRepository, foodsLocalRepository)
    }

    @Singleton
    @Provides
    fun providePreferencesUseCase(preferencesRepository: PreferencesRepository): PreferencesUseCase {
        return PreferencesUseCase(preferencesRepository)
    }

    @Singleton
    @Provides
    fun provideUsersUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(userRepository)
    }
}
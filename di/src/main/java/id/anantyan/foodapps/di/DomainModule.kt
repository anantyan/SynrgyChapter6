package id.anantyan.foodapps.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.domain.repository.FoodsLocalRepository
import id.anantyan.foodapps.domain.repository.FoodsRemoteRepository
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesRepository
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserRepository
import id.anantyan.foodapps.domain.repository.UserUseCase
import id.anantyan.foodapps.domain.repository.UsersRepository
import id.anantyan.foodapps.domain.repository.UsersUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
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
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideUsersUseCase(usersRepository: UsersRepository): UsersUseCase {
        return UsersUseCase(usersRepository)
    }
}
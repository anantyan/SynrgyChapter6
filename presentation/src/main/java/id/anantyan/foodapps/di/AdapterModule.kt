package id.anantyan.foodapps.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import id.anantyan.foodapps.presentation.detail.DetailIngredientsAdapter
import id.anantyan.foodapps.presentation.detail.DetailInstructionsAdapter
import id.anantyan.foodapps.presentation.favorite.FavoriteAdapter
import id.anantyan.foodapps.presentation.home.HomeAdapter
import id.anantyan.foodapps.presentation.home.HomeCategoriesAdapter
import id.anantyan.foodapps.presentation.profile.ProfileAdapter

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {
    @Provides
    fun provideDetailIngredientsAdapter(): DetailIngredientsAdapter {
        return DetailIngredientsAdapter()
    }

    @Provides
    fun provideDetailInstructionsAdapter(): DetailInstructionsAdapter {
        return DetailInstructionsAdapter()
    }

    @Provides
    fun provideFavoriteAdapter(): FavoriteAdapter {
        return FavoriteAdapter()
    }

    @Provides
    fun provideHomeAdapter(): HomeAdapter {
        return HomeAdapter()
    }

    @Provides
    fun provideHomeCategoriesAdapter(): HomeCategoriesAdapter {
        return HomeCategoriesAdapter()
    }

    @Provides
    fun provideProfileAdapter(): ProfileAdapter {
        return ProfileAdapter()
    }
}
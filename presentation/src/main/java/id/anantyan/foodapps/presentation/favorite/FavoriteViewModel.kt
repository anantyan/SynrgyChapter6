package id.anantyan.foodapps.presentation.favorite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.domain.model.FoodModel
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val foodsUseCase: FoodsUseCase,
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {

    fun results(): Flow<List<FoodModel>> {
        val userId: Int = runBlocking { preferencesUseCase.executeGetUserId().first() }
        return foodsUseCase.executeResults(userId)
    }
}
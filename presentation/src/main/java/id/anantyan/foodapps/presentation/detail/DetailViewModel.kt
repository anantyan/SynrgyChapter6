package id.anantyan.foodapps.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.model.FoodModel
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val foodsUseCase: FoodsUseCase,
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {
    private var _stateBookmarked: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val stateBookmarked: StateFlow<Boolean> = _stateBookmarked

    fun result(foodId: Int?): Flow<UIState<FoodModel>> = foodsUseCase.executeResult(foodId)

    fun checkFood(foodId: Int?) {
        viewModelScope.launch {
            val userId: Int = runBlocking { preferencesUseCase.executeGetUserId().first() }
            foodsUseCase.executeCheckFood(foodId, userId).collect { state ->
                when (state) {
                    is UIState.Loading -> { }
                    is UIState.Success -> {
                        _stateBookmarked.value = state.data != null
                    }
                    is UIState.Error -> {
                        _stateBookmarked.value = false
                    }
                }
            }
        }
    }

    fun bookmark(foodId: Int?) {
        viewModelScope.launch {
            result(foodId).collect { state ->
                when (state) {
                    is UIState.Loading -> { }
                    is UIState.Success -> {
                        val userId: Int = runBlocking { preferencesUseCase.executeGetUserId().first() }
                        foodsUseCase.executeBookmarkFood(state.data?.withUser(userId) ?: FoodModel())
                        _stateBookmarked.value = true
                    }
                    is UIState.Error -> {
                        _stateBookmarked.value = false
                    }
                }
            }
        }
    }

    fun unbookmark(foodId: Int?) {
        viewModelScope.launch {
            val userId: Int = runBlocking { preferencesUseCase.executeGetUserId().first() }
            foodsUseCase.executeUnbookmarkFood(foodId, userId)
            _stateBookmarked.value = false
        }
    }
}

fun FoodModel.withUser(userId: Int?): FoodModel {
    return FoodModel(analyzedInstructions, title, preparationMinutes, readyInMinutes, sourceName, servings, id, summary, image, extendedIngredients, userId)
}
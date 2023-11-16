package id.anantyan.foodapps.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.domain.model.FoodModel
import id.anantyan.foodapps.domain.model.UserModel
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val foodsUseCase: FoodsUseCase,
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {
    private var _stateIconBookmark: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val stateIconBookmark: StateFlow<Boolean> = _stateIconBookmark

    fun result(id: Int?): Flow<UIState<RecipeResponse>> = foodsUseCase.executeResult(id)

    fun checkFood(foodId: Int?): Flow<UIState<FoodModel>> {
        val userId: Int = runBlocking { preferencesUseCase.executeGetUserId().first() }
        return foodsUseCase.executeCheckFood(foodId, userId)
    }

    fun stateIconBookmark(value: Boolean) {
        _stateIconBookmark.value = value
    }

    fun bookmark(foodModel: FoodModel) {
        viewModelScope.launch {
            foodsUseCase.executeBookmarkFood(foodModel)
        }
    }

    fun unbookmark(foodId: Int?, userId: Int?) {
        viewModelScope.launch {
            foodsUseCase.executeUnbookmarkFood(foodId, userId)
        }
    }
}
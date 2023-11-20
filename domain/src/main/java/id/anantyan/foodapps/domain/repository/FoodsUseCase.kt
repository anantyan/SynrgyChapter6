package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.model.FoodModel
import kotlinx.coroutines.flow.Flow

class FoodsUseCase(
    private val foodsRemoteRepository: FoodsRemoteRepository,
    private val foodsLocalRepository: FoodsLocalRepository
) {
    fun executeResults(type: String?): Flow<UIState<List<FoodModel>>> = foodsRemoteRepository.results(type)
    fun executeResult(id: Int?): Flow<UIState<FoodModel>> = foodsRemoteRepository.result(id)

    fun executeResults(userId: Int?): Flow<List<FoodModel>> = foodsLocalRepository.results(userId)
    fun executeCheckFood(foodId: Int?, userId: Int?): Flow<UIState<FoodModel>> = foodsLocalRepository.checkFood(foodId, userId)
    suspend fun executeBookmarkFood(item: FoodModel) = foodsLocalRepository.bookmarkFood(item)
    suspend fun executeUnbookmarkFood(foodId: Int?, userId: Int?) = foodsLocalRepository.unbookmarkFood(foodId, userId)
}
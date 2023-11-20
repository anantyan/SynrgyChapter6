package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.model.FoodModel
import kotlinx.coroutines.flow.Flow

interface FoodsLocalRepository {
    fun results(userId: Int?): Flow<List<FoodModel>>
    fun checkFood(foodId: Int?, userId: Int?): Flow<UIState<FoodModel>>
    suspend fun bookmarkFood(item: FoodModel)
    suspend fun unbookmarkFood(foodId: Int?, userId: Int?)
}
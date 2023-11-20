package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.model.FoodModel
import kotlinx.coroutines.flow.Flow

interface FoodsRemoteRepository {
    fun results(type: String?): Flow<UIState<List<FoodModel>>>
    fun result(id: Int?): Flow<UIState<FoodModel>>
}
package id.anantyan.foodapps.data.remote.repository

import id.anantyan.foodapps.common.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.model.toModel
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.domain.model.FoodModel
import id.anantyan.foodapps.domain.repository.FoodsRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodsRemoteRepositoryImpl(
    private val foodsApi: FoodsApi
) : FoodsRemoteRepository {
    override fun results(type: String?): Flow<UIState<List<FoodModel>>> {
        return flow {
            emit(UIState.Loading())
            try {
                val response = foodsApi.results(type)
                if (response.isSuccessful) {
                    val item = response.body()?.results ?: emptyList()
                    emit(UIState.Success(item.map { item -> item.toModel() }))
                } else {
                    emit(UIState.Error(null, R.string.txt_invalid_get_results))
                }
            } catch (e: Exception) {
                emit(UIState.Error(null, R.string.txt_invalid_get_results))
            }
        }
    }

    override fun result(id: Int?): Flow<UIState<FoodModel>> {
        return flow {
            emit(UIState.Loading())
            try {
                val response = foodsApi.result(id)
                if (response.isSuccessful) {
                    val item = response.body() ?: RecipeResponse()
                    emit(UIState.Success(item.toModel()))
                } else {
                    emit(UIState.Error(null, R.string.txt_invalid_get_results))
                }
            } catch (e: Exception) {
                emit(UIState.Error(null, R.string.txt_invalid_get_results))
            }
        }
    }
}
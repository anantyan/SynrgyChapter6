package id.anantyan.foodapps.data.remote.repository

import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.data.remote.model.ResultsItem
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.domain.repository.FoodsRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodsRemoteRepositoryImpl(
    private val foodsApi: FoodsApi
) : FoodsRemoteRepository {
    override fun results(type: String?): Flow<UIState<List<ResultsItem>>> {
        return flow {
            emit(UIState.Loading())
            try {
                val response = foodsApi.results(type)
                if (response.isSuccessful) {
                    emit(UIState.Success(response.body()?.results ?: emptyList()))
                } else {
                    emit(UIState.Error(null, R.string.txt_invalid_get_results))
                }
            } catch (e: Exception) {
                emit(UIState.Error(null, R.string.txt_invalid_get_results))
            }
        }
    }

    override fun result(id: Int?): Flow<UIState<RecipeResponse>> {
        return flow {
            emit(UIState.Loading())
            try {
                val response = foodsApi.result(id)
                if (response.isSuccessful) {
                    emit(UIState.Success(response.body() ?: RecipeResponse()))
                } else {
                    emit(UIState.Error(null, R.string.txt_invalid_get_results))
                }
            } catch (e: Exception) {
                emit(UIState.Error(null, R.string.txt_invalid_get_results))
            }
        }
    }
}
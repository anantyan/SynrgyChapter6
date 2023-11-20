package id.anantyan.foodapps.data.local.repository

import id.anantyan.foodapps.common.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.local.dao.FoodsDao
import id.anantyan.foodapps.data.model.toEntity
import id.anantyan.foodapps.data.model.toModel
import id.anantyan.foodapps.domain.model.FoodModel
import id.anantyan.foodapps.domain.repository.FoodsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FoodsLocalRepositoryImpl(
    private val foodsDao: FoodsDao
) : FoodsLocalRepository {
    override fun results(userId: Int?): Flow<List<FoodModel>> {
        return foodsDao.selectFoods(userId).map { foodEntities ->
            foodEntities.map { foodEntity -> foodEntity.toModel() }
        }
    }

    override fun checkFood(foodId: Int?, userId: Int?): Flow<UIState<FoodModel>> {
        return flow {
            val result = foodsDao.checkFoods(foodId, userId)
            if (result != null) {
                emit(UIState.Success(result.toModel()))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_check_food))
            }
        }
    }

    override suspend fun bookmarkFood(item: FoodModel) { foodsDao.insertFoods(item.toEntity()) }
    override suspend fun unbookmarkFood(foodId: Int?, userId: Int?) { foodsDao.deleteFoods(foodId, userId) }
}
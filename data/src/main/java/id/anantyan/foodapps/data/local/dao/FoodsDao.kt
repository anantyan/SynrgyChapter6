package id.anantyan.foodapps.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.anantyan.foodapps.data.local.entities.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoods(item: FoodEntity): Long

    @Query("DELETE FROM tbl_foods WHERE id=:foodId AND userId=:userId")
    suspend fun deleteFoods(foodId: Int?, userId: Int?)

    @Query("SELECT * FROM tbl_foods WHERE id=:foodId AND userId=:userId")
    suspend fun checkFoods(foodId: Int?, userId: Int?): FoodEntity?

    @Query("SELECT * FROM tbl_foods WHERE userId=:userId")
    fun selectFoods(userId: Int?): Flow<List<FoodEntity>>
}
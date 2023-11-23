package id.anantyan.foodapps.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.anantyan.foodapps.data.local.dao.FoodsDao
import id.anantyan.foodapps.data.local.dao.UsersDao
import id.anantyan.foodapps.data.local.entities.UserEntity
import id.anantyan.foodapps.data.local.entities.FoodEntity

@Database(
    entities = [
        UserEntity::class,
        FoodEntity::class
    ], version = 3, exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun foodsDao(): FoodsDao
}
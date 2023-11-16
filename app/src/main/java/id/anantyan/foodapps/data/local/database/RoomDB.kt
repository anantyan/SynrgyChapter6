package id.anantyan.foodapps.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.anantyan.foodapps.data.local.dao.FoodsDao
import id.anantyan.foodapps.data.local.dao.UsersDao
import id.anantyan.foodapps.data.local.entities.FoodEntity
import id.anantyan.foodapps.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        FoodEntity::class
    ], version = 2, exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun foodsDao(): FoodsDao
}
package id.anantyan.foodapps.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_foods")
data class FoodEntity(
    @ColumnInfo(name = "readyInMinutes")
    val readyInMinutes: Int? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "servings")
    val servings: Int? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "userId")
    val userId: Int? = null
)
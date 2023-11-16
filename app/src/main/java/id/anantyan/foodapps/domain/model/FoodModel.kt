package id.anantyan.foodapps.domain.model

import id.anantyan.foodapps.data.local.entities.FoodEntity

data class FoodModel(
    val readyInMinutes: Int? = null,
    val image: String? = null,
    val servings: Int? = null,
    val id: Int? = null,
    val title: String? = null,
    val userId: Int? = null
)

fun FoodEntity.toModel(): FoodModel {
    return FoodModel(readyInMinutes, image, servings, id, title, userId)
}

fun FoodModel.toEntity(): FoodEntity {
    return FoodEntity(readyInMinutes, image, servings, 0, title, userId)
}

fun FoodModel.toEntityUpdate(): FoodEntity {
    return FoodEntity(readyInMinutes, image, servings, id ?: -1, title, userId)
}

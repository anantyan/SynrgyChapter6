package id.anantyan.foodapps.data.model

import id.anantyan.foodapps.data.local.entities.FoodEntity
import id.anantyan.foodapps.domain.model.FoodModel

fun FoodEntity.toModel(): FoodModel {
    return FoodModel(readyInMinutes = readyInMinutes, image = image, servings = servings, id = id, title = title, userId = userId)
}

fun FoodModel.toEntity(): FoodEntity {
    return FoodEntity(readyInMinutes = readyInMinutes, image = image, servings = servings, id = id ?: -1, title = title, userId = userId)
}
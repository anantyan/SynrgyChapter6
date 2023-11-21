package id.anantyan.foodapps.data.model

import id.anantyan.foodapps.data.local.entities.FoodEntity
import id.anantyan.foodapps.data.remote.model.AnalyzedInstructionsItem
import id.anantyan.foodapps.data.remote.model.ExtendedIngredientsItem
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.data.remote.model.ResultsItem
import id.anantyan.foodapps.data.remote.model.StepsItem
import id.anantyan.foodapps.domain.model.FoodModel

fun ResultsItem.toModel(): FoodModel {
    return FoodModel(
        readyInMinutes = readyInMinutes,
        image = image,
        servings = servings,
        id = id,
        title = title
    )
}

fun RecipeResponse.toModel(): FoodModel {
    return FoodModel(
        analyzedInstructions = analyzedInstructions?.map { it.toModel() } ?: emptyList(),
        title = title,
        readyInMinutes = readyInMinutes,
        servings = servings,
        id = id,
        summary = summary,
        image = image,
        sourceName = sourceName,
        preparationMinutes = preparationMinutes,
        extendedIngredients = extendedIngredients?.map { it.toModel() } ?: emptyList()
    )
}

fun AnalyzedInstructionsItem.toModel(): id.anantyan.foodapps.domain.model.AnalyzedInstructionsItem {
    return id.anantyan.foodapps.domain.model.AnalyzedInstructionsItem(steps?.map { it.toModel() } ?: emptyList())
}

fun StepsItem.toModel(): id.anantyan.foodapps.domain.model.StepsItem {
    return id.anantyan.foodapps.domain.model.StepsItem(step)
}

fun ExtendedIngredientsItem.toModel(): id.anantyan.foodapps.domain.model.ExtendedIngredientsItem {
    return id.anantyan.foodapps.domain.model.ExtendedIngredientsItem(original)
}
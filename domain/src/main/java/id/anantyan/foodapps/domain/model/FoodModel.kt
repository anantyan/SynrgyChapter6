package id.anantyan.foodapps.domain.model

data class FoodModel(
    val analyzedInstructions: List<AnalyzedInstructionsItem>? = null,
    val title: String? = null,
    val preparationMinutes: Int? = null,
    val readyInMinutes: Int? = null,
    val sourceName: String? = null,
    val servings: Int? = null,
    val id: Int? = null,
    val summary: String? = null,
    val image: String? = null,
    val extendedIngredients: List<ExtendedIngredientsItem>? = null,
    val userId: Int? = null
)

data class AnalyzedInstructionsItem(
    val steps: List<StepsItem>? = null
)

data class ExtendedIngredientsItem(
    val original: String? = null
)

data class StepsItem(
    val step: String? = null
)

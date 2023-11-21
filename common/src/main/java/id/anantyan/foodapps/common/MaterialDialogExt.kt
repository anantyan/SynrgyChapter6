package id.anantyan.foodapps.common

import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun MaterialAlertDialogBuilder.createListDialog(
    title: String,
    items: List<String>,
    onItemClick: ((String) -> Unit)? = null
) {
    val itemArray = items.toTypedArray()

    this.setTitle(title)
        .setItems(itemArray) { _, which ->
            val selectedItem = items[which]
            onItemClick?.invoke(selectedItem)
        }
        .setCancelable(false)
        .show()
}
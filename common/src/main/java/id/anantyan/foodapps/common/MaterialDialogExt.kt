package id.anantyan.foodapps.common

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.createListDialog(
    title: String,
    items: List<String>,
    onItemClick: ((String) -> Unit)? = null
) {
    val itemArray = items.toTypedArray()
    MaterialAlertDialogBuilder(this).setTitle(title)
        .setItems(itemArray) { _, which ->
            val selectedItem = items[which]
            onItemClick?.invoke(selectedItem)
        }
        .setCancelable(true)
        .show()
}

fun Context.createMessageDialog(
    title: String,
    message: String,
    onItemClick: (() -> Unit)? = null
) {
    MaterialAlertDialogBuilder(this).setTitle(title)
        .setMessage(message)
        .setPositiveButton("Ok") { _, _ ->
            onItemClick?.invoke()
        }
        .setCancelable(true)
        .show()
}
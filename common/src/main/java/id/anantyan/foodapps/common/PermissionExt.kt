package id.anantyan.foodapps.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Activity.permissionDialog(title: String, message: String) {
    this.createMessageDialog(title, message) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }
}
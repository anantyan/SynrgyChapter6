package id.anantyan.foodapps.common

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

fun Uri.byteArray(context: Context): ByteArray? {
    val realPath: String? = this.path(context)
    return realPath?.let { path ->
        try {
            val file = File(path)
            val inputStream: InputStream = FileInputStream(file)
            val byteBuffer = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                byteBuffer.write(buffer, 0, bytesRead)
            }

            byteBuffer.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}

fun Uri.path(context: Context): String? {
    var realPath: String? = null
    val uriScheme = this.scheme
    if (uriScheme == ContentResolver.SCHEME_FILE) {
        realPath = this.path
    } else if (uriScheme == ContentResolver.SCHEME_CONTENT) {
        val cursor: Cursor? = context.contentResolver.query(this, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex: Int = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                realPath = if (columnIndex != -1) it.getString(columnIndex) else null
            }
        }
        cursor?.close()
    }
    return realPath
}

fun String.pathPart(): MultipartBody.Part {
    val file = File(this)
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestFile)
}

fun Context.deleteAllPath() {
    val directoryPath = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath
    if (directoryPath != null) {
        val directory = File(directoryPath)
        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    file.delete()
                }
            }
        }
    }
}
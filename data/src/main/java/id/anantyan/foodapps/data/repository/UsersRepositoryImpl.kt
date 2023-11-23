package id.anantyan.foodapps.data.repository

import android.net.Uri
import android.util.Log
import id.anantyan.foodapps.common.pathPart
import id.anantyan.foodapps.data.local.datasource.UploadLocalDataSource
import id.anantyan.foodapps.data.remote.datasource.UploadRemoteDataSource
import id.anantyan.foodapps.domain.repository.UsersRepository
import okhttp3.MultipartBody

class UsersRepositoryImpl(
    private val uploadLocalDataSource: UploadLocalDataSource,
    private val uploadRemoteDataSource: UploadRemoteDataSource
) : UsersRepository {
    override suspend fun changePhoto(userId: Int, path: String): Boolean {
        return try {
            val response = uploadRemoteDataSource.uploadImage(path.pathPart())
            if (response != null) {
                uploadLocalDataSource.changeImage(userId, response.data?.url.orEmpty())
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.d("FOODAPPS-DEBUG", e.message.toString())
            false
        }
    }
}
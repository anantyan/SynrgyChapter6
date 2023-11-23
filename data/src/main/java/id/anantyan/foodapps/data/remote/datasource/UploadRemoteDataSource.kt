package id.anantyan.foodapps.data.remote.datasource

import id.anantyan.foodapps.data.remote.model.PhotoResponse
import okhttp3.MultipartBody

interface UploadRemoteDataSource {
    suspend fun uploadImage(image: MultipartBody.Part): PhotoResponse?
}
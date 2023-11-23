package id.anantyan.foodapps.data.remote.datasource

import id.anantyan.foodapps.data.remote.model.PhotoResponse
import id.anantyan.foodapps.data.remote.service.UploadApi
import okhttp3.MultipartBody

class UploadRemoteDataSourceImpl(
    private val uploadApi: UploadApi
) : UploadRemoteDataSource {
    override suspend fun uploadImage(image: MultipartBody.Part): PhotoResponse? {
        val response = uploadApi.uploadImage(image = image)
        return if (response.isSuccessful) {
            response.body()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }
}
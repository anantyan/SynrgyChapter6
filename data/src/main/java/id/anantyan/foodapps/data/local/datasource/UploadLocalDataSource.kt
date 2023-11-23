package id.anantyan.foodapps.data.local.datasource

interface UploadLocalDataSource {
    suspend fun changeImage(userId: Int, image: String)
}
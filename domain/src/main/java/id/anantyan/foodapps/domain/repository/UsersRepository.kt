package id.anantyan.foodapps.domain.repository

interface UsersRepository {
    suspend fun changePhoto(userId: Int, path: String): Boolean
}
package id.anantyan.foodapps.domain.repository

class UsersUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun executeChangePhoto(userId: Int, path: String): Boolean = usersRepository.changePhoto(userId, path)
}
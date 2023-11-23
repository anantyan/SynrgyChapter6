package id.anantyan.foodapps.data.local.datasource

import id.anantyan.foodapps.data.local.dao.UsersDao

class UploadLocalDataSourceImpl(
    private val usersDao: UsersDao
) : UploadLocalDataSource {
    override suspend fun changeImage(userId: Int, image: String) {
        usersDao.changePhoto(userId, image)
    }
}
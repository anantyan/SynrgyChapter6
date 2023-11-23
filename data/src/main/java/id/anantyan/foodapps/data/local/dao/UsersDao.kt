package id.anantyan.foodapps.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.anantyan.foodapps.data.local.entities.UserEntity

@Dao
interface UsersDao {
    @Query("SELECT * FROM tbl_users WHERE email=:email AND password=:password")
    suspend fun login(email: String?, password: String?): UserEntity?

    @Query("SELECT * FROM tbl_users WHERE email=:email OR username=:username")
    suspend fun duplicateUser(email: String?, username: String?): UserEntity?

    @Query("SELECT * FROM tbl_users WHERE id=:id")
    suspend fun profile(id: Int?): UserEntity?

    @Query("UPDATE tbl_users SET username=:username, email=:email, password=:password WHERE id=:userId")
    suspend fun changeProfile(userId: Int, username: String?, email: String?, password: String?)

    @Query("UPDATE tbl_users SET image=:image WHERE id=:userId")
    suspend fun changePhoto(userId: Int, image: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun register(item: UserEntity): Long
}
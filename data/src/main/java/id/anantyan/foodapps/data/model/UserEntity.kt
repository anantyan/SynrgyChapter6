package id.anantyan.foodapps.data.model

import id.anantyan.foodapps.data.local.entities.UserEntity
import id.anantyan.foodapps.domain.model.UserModel


fun UserEntity.toModel(): UserModel {
    return UserModel(id = id, username = username, email = email, image = image)
}

fun UserModel.toEntity(): UserEntity {
    return UserEntity(id = 0, username = username, email = email, password = password)
}
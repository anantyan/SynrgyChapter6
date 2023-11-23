package id.anantyan.foodapps.data.remote.model

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("data")
    val data: Data? = null,

    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("status")
    val status: Int? = null
)

data class Data(
    @SerializedName("display_url")
    val displayUrl: String? = null,

    @SerializedName("image")
    val image: Image? = null,

    @SerializedName("thumb")
    val thumb: Thumb? = null,

    @SerializedName("delete_url")
    val deleteUrl: String? = null,

    @SerializedName("medium")
    val medium: Medium? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url_viewer")
    val urlViewer: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("size")
    val size: String? = null,

    @SerializedName("width")
    val width: String? = null,

    @SerializedName("expiration")
    val expiration: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("time")
    val time: String? = null,

    @SerializedName("height")
    val height: String? = null
)

data class Image(
    @SerializedName("extension")
    val extension: String? = null,

    @SerializedName("filename")
    val filename: String? = null,

    @SerializedName("mime")
    val mime: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("url")
    val url: String? = null
)

data class Thumb(
    @SerializedName("extension")
    val extension: String? = null,

    @SerializedName("filename")
    val filename: String? = null,

    @SerializedName("mime")
    val mime: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("url")
    val url: String? = null
)

data class Medium(
    @SerializedName("extension")
    val extension: String? = null,

    @SerializedName("filename")
    val filename: String? = null,

    @SerializedName("mime")
    val mime: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("url")
    val url: String? = null
)

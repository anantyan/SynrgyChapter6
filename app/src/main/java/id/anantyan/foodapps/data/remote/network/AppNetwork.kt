package id.anantyan.foodapps.data.remote.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.Date
import java.util.concurrent.TimeUnit

object AppNetwork {
    const val BASE_URL = "https://api.spoonacular.com/"
    const val BASE_IMAGE = "https://spoonacular.com/recipeImages/"
    const val API_KEY = "637eca6d936d43969f1f0f14c4bb1308"
}
package id.anantyan.foodapps.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.anantyan.foodapps.data.remote.network.AppNetwork
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.data.remote.service.UploadApi
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().apply {
            setLenient()
            registerTypeAdapter(Date::class.java, JsonDeserializer { jsonElement, _, _ ->
                Date(jsonElement.asJsonPrimitive.asLong)
            })
        }.create()
    }

    @Singleton
    @Provides
    fun providerHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Singleton
    @Provides
    fun providerHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            addNetworkInterceptor(httpLoggingInterceptor)
            addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            cookieJar(JavaNetCookieJar(CookieManager()))
            connectTimeout(15, TimeUnit.MINUTES)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    @Named("RECIPE")
    fun provideRecipeRetrofit(
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder().apply {
            client(httpClient)
            baseUrl(AppNetwork.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

    @Singleton
    @Provides
    @Named("UPLOAD")
    fun provideUploadRetrofit(
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder().apply {
            client(httpClient)
            baseUrl(AppNetwork.BASE_UPLOAD)
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

    @Singleton
    @Provides
    @Named("RECIPE")
    fun provideRecipeApi(@Named("RECIPE") retrofit: Retrofit): FoodsApi {
        return retrofit.create(FoodsApi::class.java)
    }

    @Singleton
    @Provides
    @Named("UPLOAD")
    fun provideUploadApi(@Named("UPLOAD") retrofit: Retrofit): UploadApi {
        return retrofit.create(UploadApi::class.java)
    }
}
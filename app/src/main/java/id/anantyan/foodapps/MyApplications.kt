package id.anantyan.foodapps

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import id.anantyan.foodapps.work.UploadWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class MyApplications : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: UploadWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        val factory = DelegatingWorkerFactory()
        factory.addFactory(workerFactory)
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(factory)
            .build()
    }
}
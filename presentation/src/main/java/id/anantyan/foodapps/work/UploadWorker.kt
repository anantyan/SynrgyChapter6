package id.anantyan.foodapps.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import id.anantyan.foodapps.common.deleteAllPath
import id.anantyan.foodapps.common.statusNotification
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UsersUseCase
import id.anantyan.foodapps.common.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val IMAGE_PATH = "IMAGE_PATH"

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted worker: WorkerParameters,
    @Assisted private val preferencesUseCase: PreferencesUseCase,
    @Assisted private val usersUseCase: UsersUseCase
) : CoroutineWorker(context, worker) {
    override suspend fun doWork(): Result {
        statusNotification(context.getString(R.string.txt_progress_upload), context.getString(R.string.txt_progress_upload_msg), context)
        val path = inputData.getString(IMAGE_PATH)
        val userId = runBlocking { preferencesUseCase.executeGetUserId().first() }
        val response = usersUseCase.executeChangePhoto(userId, path ?: "")
        return if (response) {
            statusNotification(context.getString(R.string.txt_success_upload), context.getString(R.string.txt_success_upload_msg), context)
            context.deleteAllPath()
            Result.success()
        } else {
            statusNotification(context.getString(R.string.txt_invalid_upload), context.getString(R.string.txt_invalid_upload_msg), context)
            context.deleteAllPath()
            Result.failure()
        }
    }
}

fun Context.uploadWorker(path: String?) {
    val uploadData = Data.Builder()
        .putString(IMAGE_PATH, path)
        .build()

    val uploadConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
        .setInputData(uploadData)
        .setConstraints(uploadConstraints)
        .build()

    WorkManager.getInstance(this).enqueue(uploadWorkRequest)
}

class UploadWorkerFactory @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val preferencesUseCase: PreferencesUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return UploadWorker(appContext, workerParameters, preferencesUseCase, usersUseCase)
    }
}
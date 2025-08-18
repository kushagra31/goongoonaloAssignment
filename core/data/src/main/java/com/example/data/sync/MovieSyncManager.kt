package com.example.data.sync

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Reports on if synchronization is in progress
 */
interface SyncManager {
    val isSyncing: Flow<Boolean>
    fun requestSync()
    fun requestPeriodicSync()
}

class MovieSyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) : SyncManager {
    override val isSyncing: Flow<Boolean>
        get() = TODO("Not yet implemented")

    override fun requestSync() {

        val workManager = WorkManager
            .getInstance(context)

        workManager.enqueueUniqueWork(
            USER_SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.startUpSyncWork()
        )
    }

    override fun requestPeriodicSync() {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "UniquePeriodicSync",
            ExistingPeriodicWorkPolicy.KEEP,
            SyncWorker.schedulePeriodicWork()
        )
    }
}

internal const val USER_SYNC_WORK_NAME = "UserWorkName"
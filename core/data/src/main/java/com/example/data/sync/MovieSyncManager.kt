package com.example.data.sync

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    override val isSyncing: Flow<Boolean> = WorkManager
        .getInstance(context)
        .getWorkInfosForUniqueWorkLiveData(ONETIME_MOVIES_SYNC_WORK_NAME)
        .asFlow().map { workInfos ->
            !(workInfos[0]?.state?.isFinished ?: true)
        }

    override fun requestSync() {
        val workManager = WorkManager
            .getInstance(context)
        workManager.enqueueUniqueWork(
            ONETIME_MOVIES_SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.startUpSyncWork()
        )
    }

    override fun requestPeriodicSync() {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            PERIODIC_MOVIES_SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            SyncWorker.schedulePeriodicWork()
        )
    }
}

internal const val ONETIME_MOVIES_SYNC_WORK_NAME = "OneTimeMoviesSyncWorkName"
internal const val PERIODIC_MOVIES_SYNC_WORK_NAME = "PeriodicMoviesSyncWorkName"
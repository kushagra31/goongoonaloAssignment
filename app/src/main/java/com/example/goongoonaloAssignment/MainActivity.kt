package com.example.goongoonaloAssignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.customviews.ConfigurableText
import com.example.data.sync.SyncWorker
import com.example.goongoonaloAssignment.moviesList.MoviesListRoute
import com.example.goongoonaloAssignment.navigation.SampleAppNavHost
import com.example.goongoonaloAssignment.ui.PCATheme
import com.example.network.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniqueWork(
            "UserWorkName",
            ExistingWorkPolicy.KEEP,
            SyncWorker.startUpSyncWork()
        )
        setContent {
            val appState = rememberSampleAppState(networkMonitor)
            val workInfos = workManager
                .getWorkInfosForUniqueWorkLiveData("UserWorkName")
                .observeAsState().value

            val workInfoSuccess = workInfos?.get(0)?.state?.isFinished ?: false
            PCATheme {
                SampleApp(appState, workInfoSuccess = workInfoSuccess)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleApp(
    appState: SampleAppState,
    startDestination: Any = MoviesListRoute,
    modifier: Modifier = Modifier,
    workInfoSuccess: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val destination = appState.currentTopLevelDestination
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    // If user is not connected to the internet show a snack bar to inform them.
    val notConnectedMessage = stringResource(R.string.not_connected)
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }
    Scaffold(
        contentColor = Color.Blue,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    ConfigurableText(
                        destination?.titleText ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (!workInfoSuccess) CircularProgressIndicator(modifier.align(Alignment.Center)) else SampleAppNavHost(appState, startDestination, modifier)
            }
        },
    )
}


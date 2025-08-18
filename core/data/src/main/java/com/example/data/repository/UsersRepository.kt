//package com.example.data.repository
//
//import android.util.Log
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import com.example.data.UsersPagingSource
//import com.example.data.sync.SyncManager
//import com.example.data.sync.Syncable
//import com.example.data.sync.Synchronizer
//import com.example.model.models.User
//import com.example.model.models.UserEntity
//import com.example.model.models.asEntity
//import com.example.model.models.asExternalModel
//import com.example.model.models.asUsersRequest
//import com.example.network.NetworkMonitor
//import com.example.network.UsersApiService
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//
//interface UsersRepository : Syncable {
//    fun getUsers(): Flow<PagingData<Data>>
//
//    suspend fun createUsers(user: User)
//
//    fun getAllUnSyncedItemsStream(): Flow<List<User>>
//
//    suspend fun insertUser(user: UserEntity)
//
//}
//
//class UsersRepositoryImpl @Inject constructor(
//    private val usersApiService: UsersApiService,
//    private val userDao: UserDao,
//    private val syncManager: SyncManager,
//    private val networkMonitor: NetworkMonitor
//) : UsersRepository {
//    override fun getUsers() =
//        Pager(
//            config = PagingConfig(
//                pageSize = 100,
//            ),
//            pagingSourceFactory = {
//                UsersPagingSource(usersApiService)
//            }
//        ).flow
//
//    override suspend fun createUsers(user: User) {
//        networkMonitor.isOnline.collect{ isOnline ->
//            if (isOnline) usersApiService.createUser(user.asUsersRequest())
//            else {
//                insertUser(user.asEntity())
//                syncManager.requestSync()
//            }
//        }
//    }
//
//    override fun getAllUnSyncedItemsStream(): Flow<List<User>> = userDao.getAllUnSyncedItems().map {
//        it.map(UserEntity::asExternalModel)
//    }
//
//    override suspend fun insertUser(user: UserEntity) = userDao.insert(user)
//    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
//        val unSyncedTasks = getAllUnSyncedItemsStream()
//        unSyncedTasks.collect { users ->
//            users.forEach { user ->
//                try {
//                    usersApiService.createUser(user.asUsersRequest())
//                } catch (e: Exception) {
//                    Log.e("SyncException", e.message.toString())
//                }
//            }
//        }
//        return true
//    }
//}
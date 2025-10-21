package com.zagirlek.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zagirlek.common.model.UserStatus
import com.zagirlek.local.database.NyTimesDatabase
import com.zagirlek.local.user.entitiy.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoreLocalModule(private val applicationContext: Context) {
    private val database: NyTimesDatabase by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = NyTimesDatabase::class.java,
            name = "nyTimesDatabase"
        )
            .fallbackToDestructiveMigration(true)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val userDao = database.userDao()
                        if (userDao.getAllList().isEmpty())
                            userDao.insertAll(
                                userList = listOf(
                                    UserEntity(
                                        phone = "48392015432532",
                                        nickname = "Логин_Админа",
                                        status = UserStatus.ADMIN,
                                        password = "Пароль123"
                                    ),
                                    UserEntity(
                                        phone = "483128492134",
                                        nickname = "Логин_Клиента",
                                        status = UserStatus.CLIENT,
                                        password = "Пароль123"
                                    )
                                )
                            )
                    }
                }
            })
            .build()
    }

    fun cityDao() = database.cityDao()
    fun weatherDao() = database.weatherDao()
    fun remoteKeyDao() = database.remoteKeyDao()
    fun userDao() = database.userDao()
    fun articleStatusDao() = database.articleStatusDao()
    fun articleLiteDao() = database.articleLiteDao()
    fun articleFullDao() = database.articleFullDao()

}
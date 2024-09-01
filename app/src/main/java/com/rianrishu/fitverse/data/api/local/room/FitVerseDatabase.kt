package com.rianrishu.fitverse.data.api.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rianrishu.fitverse.data.model.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class FitVerseDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDAO
}

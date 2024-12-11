package com.rtl.petkinfe.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rtl.petkinfe.data.local.dao.PhotoDao
import com.rtl.petkinfe.data.local.entity.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}
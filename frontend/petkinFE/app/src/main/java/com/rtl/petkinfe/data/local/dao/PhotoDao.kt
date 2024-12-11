package com.rtl.petkinfe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rtl.petkinfe.data.local.entity.Photo
import java.io.File

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: Photo)

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getPhotoById(id: Int): Photo?

    @Query("DELETE FROM photos")
    suspend fun deleteAllPhotos()

    @Query("SELECT * FROM photos WHERE uri = :uri LIMIT 1")
    suspend fun getPhotoByUri(uri: String): Photo?


    @Query(
        """
        UPDATE photos 
        SET 
            predictionId = :predictionId,
            probabilityA1 = :probA1,
            probabilityA2 = :probA2,
            probabilityA3 = :probA3,
            probabilityA4 = :probA4,
            probabilityA5 = :probA5,
            probabilityA6 = :probA6,
            probabilityA7 = :probA7
        WHERE uri = :uri
        """
    )
    suspend fun updatePhoto(
        uri: String,
        predictionId: Long?,
        probA1: Float?,
        probA2: Float?,
        probA3: Float?,
        probA4: Float?,
        probA5: Float?,
        probA6: Float?,
        probA7: Float?
    )
}

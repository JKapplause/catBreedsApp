package com.info.catbreeds.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.info.catbreeds.model.Cat

@Dao
interface CatDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg cats : Cat) : List<Long>


    @Query("SELECT * FROM cat")
    suspend fun getAllCats() : List<Cat>

    @Query("SELECT * FROM cat WHERE uuid = :catId")
    suspend fun getCat(catId : Int ): Cat

    @Query("DELETE FROM cat")
    suspend fun deleteAllCats()
}
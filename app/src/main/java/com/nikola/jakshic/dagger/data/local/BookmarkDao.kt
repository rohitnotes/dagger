package com.nikola.jakshic.dagger.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.nikola.jakshic.dagger.model.Bookmark
import com.nikola.jakshic.dagger.model.Player

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmark INNER JOIN players WHERE bookmark.account_id = players.account_id")
    fun getPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM bookmark INNER JOIN players WHERE bookmark.account_id = :id")
    fun getPlayer(id: Long): LiveData<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmark WHERE bookmark.account_id = :id")
    fun removeFromBookmark(id: Long)
}
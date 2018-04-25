package com.nikola.jakshic.dagger.ui.bookmark

import android.arch.lifecycle.ViewModel
import com.nikola.jakshic.dagger.data.local.PlayerDao
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(dao: PlayerDao) : ViewModel() {

    val list = dao.getPlayers()
}
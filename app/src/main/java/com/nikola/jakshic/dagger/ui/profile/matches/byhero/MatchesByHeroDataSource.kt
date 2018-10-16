package com.nikola.jakshic.dagger.ui.profile.matches.byhero

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.nikola.jakshic.dagger.data.remote.OpenDotaService
import com.nikola.jakshic.dagger.ui.Status
import com.nikola.jakshic.dagger.vo.Match
import kotlinx.coroutines.experimental.runBlocking

class MatchesByHeroDataSource(
        private val accountId: Long,
        private val heroId: Int,
        private val service: OpenDotaService) : PositionalDataSource<Match>() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    var retry: (() -> Any)? = null
        get() {
            val tmp = field
            field = null
            return tmp
        }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Match>) {
        runBlocking {
            try {
                _status.postValue(Status.LOADING)
                val matches = service.getMatchesByHero(accountId, heroId, 60, 0).await()
                callback.onResult(matches, 0)
                _status.postValue(Status.SUCCESS)
                retry = null
            } catch (e: Exception) {
                retry = { loadInitial(params, callback) }
                _status.postValue(Status.ERROR)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Match>) {
        runBlocking {
            try {
                _status.postValue(Status.LOADING)
                val offset = params.startPosition
                val matches = service.getMatchesByHero(accountId, heroId, 20, offset).await()
                callback.onResult(matches)
                _status.postValue(Status.SUCCESS)
                retry = null
            } catch (e: Exception) {
                retry = { loadRange(params, callback) }
                _status.postValue(Status.ERROR)
            }
        }
    }
}
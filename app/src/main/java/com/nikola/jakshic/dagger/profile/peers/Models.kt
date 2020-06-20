package com.nikola.jakshic.dagger.profile.peers

import com.nikola.jakshic.dagger.common.sqldelight.Peers
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeerJson(
    var accountId: Long = 0,
    @Json(name = "account_id") var peerId: Long,
    @Json(name = "personaname") var personaname: String?,
    @Json(name = "avatarfull") var avatarfull: String?,
    @Json(name = "with_games") var withGames: Long,
    @Json(name = "with_win") var withWin: Long
)

data class PeerUI(
    val peerId: Long,
    val personaname: String?,
    val avatarfull: String?,
    val withGames: Long,
    val withWin: Long
)

fun PeerJson.mapToDb(): Peers {
    return Peers(
        account_id = this.accountId,
        peer_id = this.peerId,
        persona_name = this.personaname,
        avatar_url = this.avatarfull,
        games = this.withGames,
        wins = this.withWin
    )
}

fun mapToUi(
    account_id: Long,
    peer_id: Long,
    persona_name: String?,
    avatar_url: String?,
    games: Long,
    wins: Long
): PeerUI {
    return PeerUI(
        peerId = peer_id,
        personaname = persona_name,
        avatarfull = avatar_url,
        withGames = games,
        withWin = wins
    )
}
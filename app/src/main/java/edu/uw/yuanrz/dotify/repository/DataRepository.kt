package edu.uw.yuanrz.dotify.repository

import edu.uw.yuanrz.dotify.model.MusicLibrary
import edu.uw.yuanrz.dotify.model.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//Where you acquire data no matter how(import from local file or call web json or sth.
// Just get that data
class DataRepository {

    private val MusicService = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicService::class.java)

    suspend fun fetchSongs(): MusicLibrary = MusicService.fetchSongs()
    suspend fun getUser() = MusicService.getUser()
}


interface MusicService {

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    suspend fun fetchSongs(): MusicLibrary


    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getUser(): UserInfo

}
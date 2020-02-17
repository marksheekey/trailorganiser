package co.uk.happyapper.trailorganiser.komoot

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface KomootAuthServiceInterface{
    @Headers(
        "Accept: application/json")
    @POST("oauth/token")
    fun getAccessToken(@Query("redirect_uri") redirect: String = redirect_url,
                                @Query("grant_type") grant_type: String = "authorization_code",
                                @Query("code") code: String): Deferred<Response<KomootAuth>>

    @POST("oauth/token")
    fun refreshToken(@Query("refresh_token") refresh_token: String, @Query("grant_type") grantype: String = "refresh_token")

}
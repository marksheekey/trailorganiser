package co.uk.happyapper.trailorganiser.komoot

import android.provider.Telephony.Carriers.BEARER
import com.google.common.net.HttpHeaders.AUTHORIZATION
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val komoot_auth = "https://auth.komoot.de/"
val redirect_url = "happyapperurl"

class KomootAuthService(private val komootData: KomootDataInterface ) {
    val build: KomootAuthServiceInterface by lazy { makeRetrofitService(AuthenticationInterceptor(komootData))}
    fun makeRetrofitService(authenticationInterceptor: AuthenticationInterceptor? = null): KomootAuthServiceInterface {
        return Retrofit.Builder()
            .baseUrl(komoot_auth)
            .client(createOkHttpClientBuilder(authenticationInterceptor).build())
            .addConverterFactory(GsonConverterFactory.create(setGsonRules()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(KomootAuthServiceInterface::class.java)
    }

    private fun createOkHttpClientBuilder(authenticationInterceptor: AuthenticationInterceptor?): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
        authenticationInterceptor?.let { okHttpClientBuilder.addInterceptor(it) }
        return okHttpClientBuilder
    }

    class AuthenticationInterceptor(
        private val komootData: KomootDataInterface,
        val username: String? = null,
        private val password: String? = null
    ) :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val builder = original.newBuilder()
            if (username != null && password != null) {
                builder.header("Authorization", Credentials.basic(username.orEmpty(), password))
            } else if (!komootData.refresh_token.isNullOrBlank()) {
                builder.header(AUTHORIZATION, BEARER + " ${komootData.refresh_token}")
            }
            return chain.proceed(builder.build())
        }
    }

    fun authByLogin(userName: String, password: String): KomootAuthServiceInterface {
        return makeRetrofitService(AuthenticationInterceptor(komootData, userName, password))
    }


    private fun setGsonRules(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .serializeNulls()
            .create()
}
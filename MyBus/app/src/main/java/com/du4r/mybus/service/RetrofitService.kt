package com.du4r.mybus.service

import com.du4r.mybus.BuildConfig
import com.du4r.mybus.models.Line
import com.du4r.mybus.models.Stoppage
import com.du4r.mybus.models.Position
import com.du4r.mybus.models.Forecast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @POST("Login/Autenticar")
    suspend fun auth(
        @Query("token") token: String,
    ): Response<Boolean>

    @GET("Posicao")
    fun getPositions(): Call<Position>

    @GET("Previsao")
    fun getPosicaoVeiculos(
        @Header("Content-Type") contentType: String?,
        @Header("Authorization") authToken: String?,
        @Query("codigoLinha") codigoLinha: String?
    ): Call<Position?>?

    @GET("Linha/Buscar")
    fun getLines(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Query("termosBusca") termosBusca: String
    ): Call<List<Line>>

    @GET("Parada/Buscar")
    fun getStoppages(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Query("termosBusca") termosBusca: String
    ): Call<List<Stoppage>>

    @GET("Previsao/Parada")
    fun getForecasts(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authToken: String,
        @Query("codigoParada") codigoParada: Int
    ): Call<Forecast>


    companion object {

        private val retrofitService: RetrofitService by lazy {

            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            var client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.url_with_proxy)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)
        }

        fun getInstance(): RetrofitService {
            return retrofitService
        }
    }
}
package com.du4r.mybus.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.du4r.mybus.BuildConfig
import com.du4r.mybus.models.Line
import com.du4r.mybus.models.Stoppage
import com.du4r.mybus.models.Position
import com.du4r.mybus.models.Forecast
import com.du4r.mybus.service.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK

class MainViewModel(private val retrofitService: RetrofitService) : ViewModel() {

    val isAuth = MutableLiveData(false)
    val position = MutableLiveData<Position>()
    val lines = MutableLiveData<List<Line>>()
    val erros = MutableLiveData<List<String>>()
    val stoppages = MutableLiveData<List<Stoppage>>()
    val forecast = MutableLiveData<Forecast>()

    init {
        authApi()
        Log.d("EDBUG","viemodel iniciado")
    }

    // 1 Obter a posicao de todos os onibus
    fun getPosicoes() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = retrofitService.getPositions()

            withContext(Dispatchers.Main) {
                request.enqueue(object : Callback<Position> {
                    override fun onResponse(
                        call: Call<Position>,
                        response: Response<Position>
                    ) {
                        if (response.code() == HTTP_OK) {
                            position.postValue(response.body())
                        } else {
                            Log.d("EDBUG", response.message() + response.code())
                        }
                    }

                    override fun onFailure(call: Call<Position>, t: Throwable) {
                        Log.d("EDBUG", t.message.toString())
                    }
                })
            }
        }
    }

    // 2 - Obter informacoes das linhas
    fun getLinhas(termoBusca: String) {
        viewModelScope.launch (Dispatchers.IO){
            val request = retrofitService.getLines("application/json",BuildConfig.olho_vivo_token, termoBusca)
            withContext(Dispatchers.Main){
                request.enqueue(object : Callback<List<Line>> {
                    override fun onResponse(
                        call: Call<List<Line>>,
                        response: Response<List<Line>>
                    ) {
                        if (response.isSuccessful) {
                            lines.postValue(response.body())
                        } else {
                            Log.d("EDBUG", response.message() + response.code())
                        }
                    }
                    override fun onFailure(call: Call<List<Line>>, t: Throwable) {
                        Log.d("EDBUG", t.message.toString())
                    }
                })
            }
        }
    }

    //3 Obter dados das paradas
    fun getParadas(termoBusca: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = retrofitService.getStoppages("application/json", BuildConfig.olho_vivo_token, termoBusca)
            withContext(Dispatchers.Main){
                request.enqueue(object : Callback<List<Stoppage>> {
                    override fun onResponse(
                        call: Call<List<Stoppage>>,
                        response: Response<List<Stoppage>>
                    ) {
                        if (response.isSuccessful) {
                            stoppages.postValue(response.body())
                        } else {
                            Log.d("EDBUG", response.message() + response.code())
                        }
                    }

                    override fun onFailure(call: Call<List<Stoppage>>, t: Throwable) {
                        Log.d("EDBUG", t.message.toString())
                    }
                })
            }
        }
    }

    // 4 Obter previsao de chegada de uma parada
    fun getPrevisaoParada(authToken: String, codigoParada: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = retrofitService.getForecasts("application/json", authToken, codigoParada)
            withContext(Dispatchers.Main){
                request.enqueue(object : Callback<Forecast> {
                    override fun onResponse(
                        call: Call<Forecast>,
                        response: Response<Forecast>
                    ) {
                        if (response.isSuccessful) {
                            val previsaoParada = response.body()
                            // Lógica para lidar com a resposta, como exibir previsões
                        } else {
                            // Lógica para lidar com erros
                        }
                    }

                    override fun onFailure(call: Call<Forecast>, t: Throwable) {
                        // Lógica para lidar com falhas na requisição
                    }
                })
            }
        }
    }

    fun authApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retrofitService.auth(BuildConfig.olho_vivo_token)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        isAuth.postValue(response.body())
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.d("EDBUG", "Requisicao falhou: " + response.message())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Log.d("EDBUG", "Erro inesperado: " + e.message.toString())
                }
            }
        }
    }
}
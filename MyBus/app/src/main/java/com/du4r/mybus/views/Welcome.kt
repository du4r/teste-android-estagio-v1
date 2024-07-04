package com.du4r.mybus.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.du4r.mybus.BuildConfig
import com.du4r.mybus.R
import com.du4r.mybus.service.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Welcome : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var logoImg: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        setupViews()

    }

    fun authApi() {
        val client = RetrofitService.getInstance()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.auth(BuildConfig.olho_vivo_token)
                if (response.isSuccessful) {
                    Log.d("EDBUG",response.body().toString())
                    withContext(Dispatchers.Main) {
                        goToMainScreen()
                        progressBar.visibility = View.GONE
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Welcome, "Erro inesperado", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Welcome, "Erro inesperado", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    fun setupViews() {
        btnStart = findViewById(R.id.btn_start)
        logoImg = findViewById(R.id.logo_img)
        progressBar = findViewById(R.id.progress)

        logoImg.animate().apply {
            duration = 1000
            startDelay = 1000
            rotation(360f)
            alpha(1f)
            start()
        }

        btnStart.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            //authApi()
            goToMainScreen()
        }
    }

    fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        progressBar.visibility = View.GONE
    }
}
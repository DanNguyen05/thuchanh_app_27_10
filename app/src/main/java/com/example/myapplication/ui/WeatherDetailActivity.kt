package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlin.random.Random

class WeatherDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        val city = intent.getStringExtra("CITY") ?: "Hà Nội"
        val tvCity: TextView = findViewById(R.id.tvCity)
        val tvTemp: TextView = findViewById(R.id.tvTemp)
        val tvHumidity: TextView = findViewById(R.id.tvHumidity)
        val tvStatus: TextView = findViewById(R.id.tvStatus)
        val btnBack: Button = findViewById(R.id.btnBack)

        val temp = Random.nextInt(25, 36)
        val humidity = Random.nextInt(60, 95)
        val weatherTypes = listOf("☀️ Nắng", "⛅ Có mây", "🌧️ Mưa", "⛈️ Dông")
        val status = weatherTypes.random()

        tvCity.text = city
        tvTemp.text = "$temp°C"
        tvHumidity.text = "$humidity%"
        tvStatus.text = status

        btnBack.setOnClickListener {
            finish()
        }
    }
}


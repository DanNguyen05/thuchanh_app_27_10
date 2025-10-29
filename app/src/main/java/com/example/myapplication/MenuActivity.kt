package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_menu)

            // Bài 1: Máy tính Mini
            findViewById<LinearLayout>(R.id.card_calculator).setOnClickListener {
                try {
                    startActivity(Intent(this, com.example.myapplication.ui.CalculatorActivity::class.java))
                } catch (e: Exception) {
                    Toast.makeText(this, "Chức năng Máy tính đang được phát triển", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            // Bài 2: Thư viện ảnh
            findViewById<LinearLayout>(R.id.card_gallery).setOnClickListener {
                try {
                    startActivity(Intent(this, com.example.myapplication.ui.GalleryActivity::class.java))
                } catch (e: Exception) {
                    Toast.makeText(this, "Chức năng Thư viện ảnh đang được phát triển", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            // Bài 3: Thời tiết
            findViewById<LinearLayout>(R.id.card_weather).setOnClickListener {
                try {
                    startActivity(Intent(this, com.example.myapplication.ui.WeatherActivity::class.java))
                } catch (e: Exception) {
                    Toast.makeText(this, "Chức năng Thời tiết đang được phát triển", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            // Bài 4: S-Task
            findViewById<LinearLayout>(R.id.card_task).setOnClickListener {
                try {
                    startActivity(Intent(this, com.example.myapplication.ui.TaskActivity::class.java))
                } catch (e: Exception) {
                    Toast.makeText(this, "Chức năng Quản lý công việc đang được phát triển", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi khởi tạo: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}

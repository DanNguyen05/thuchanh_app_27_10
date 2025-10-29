package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val spinner: Spinner = findViewById(R.id.citySpinner)
        val btnCheck: Button = findViewById(R.id.btnCheckWeather)

        val cities = arrayOf("Hà Nội", "TP.HCM", "Đà Nẵng", "Cần Thơ", "Hải Phòng", "Nha Trang", "Huế", "Vũng Tàu")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)

        btnCheck.setOnClickListener {
            val city = spinner.selectedItem.toString()
            val intent = Intent(this, WeatherDetailActivity::class.java)
            intent.putExtra("CITY", city)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 1, 0, "Giới thiệu ứng dụng")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            1 -> {
                showAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Giới thiệu ứng dụng")
            .setMessage("Ứng dụng Thời Tiết\n\n" +
                    "Phiên bản: 1.0\n" +
                    "Ứng dụng hiển thị thông tin thời tiết các thành phố tại Việt Nam.\n\n" +
                    "Chức năng:\n" +
                    "- Chọn thành phố\n" +
                    "- Xem thông tin thời tiết chi tiết\n" +
                    "- Hiển thị nhiệt độ, độ ẩm, gió, tầm nhìn\n\n" +
                    "Phát triển bởi: Sinh viên CNTT")
            .setPositiveButton("Đóng", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
}


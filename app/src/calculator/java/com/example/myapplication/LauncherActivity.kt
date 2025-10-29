package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.CalculatorActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Chuyển thẳng đến Calculator
        startActivity(Intent(this, CalculatorActivity::class.java))
        finish()
    }
}

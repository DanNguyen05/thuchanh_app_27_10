package com.example.myapplication.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class CalculatorActivity : AppCompatActivity() {
    private var currentNumber = ""
    private var previousNumber = ""
    private var operator = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val tvResult: TextView = findViewById(R.id.tvResult)
        val tvInput: TextView = findViewById(R.id.tvInput)

        // Number buttons
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        numberButtons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                appendNumber(index.toString(), tvResult, tvInput)
            }
        }

        // Dot button
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            appendDot(tvResult, tvInput)
        }

        // Operation buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            setOperation("+", tvResult, tvInput)
        }

        findViewById<Button>(R.id.btnSubtract).setOnClickListener {
            setOperation("-", tvResult, tvInput)
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            setOperation("×", tvResult, tvInput)
        }

        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            setOperation("÷", tvResult, tvInput)
        }

        // Equals button
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calculateResult(tvResult, tvInput)
        }

        // Clear button
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clear(tvResult, tvInput)
        }

        // Plus/Minus button
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            toggleSign(tvResult, tvInput)
        }

        // Percent button
        findViewById<Button>(R.id.btnPercent).setOnClickListener {
            applyPercent(tvResult, tvInput)
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
            .setMessage("Ứng dụng Máy Tính Mini\n\n" +
                    "Phiên bản: 1.0\n" +
                    "Ứng dụng tính toán đơn giản.\n\n" +
                    "Chức năng:\n" +
                    "- Cộng, trừ, nhân, chia\n" +
                    "- Số thập phân\n" +
                    "- Đổi dấu +/-\n" +
                    "- Phần trăm %\n\n" +
                    "Phát triển bởi: Sinh viên CNTT")
            .setPositiveButton("Đóng", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }

    private fun appendNumber(number: String, tvResult: TextView, tvInput: TextView) {
        if (isNewOperation) {
            currentNumber = number
            isNewOperation = false
        } else {
            currentNumber += number
        }
        updateDisplay(tvResult, tvInput)
    }

    private fun appendDot(tvResult: TextView, tvInput: TextView) {
        if (isNewOperation) {
            currentNumber = "0."
            isNewOperation = false
        } else if (!currentNumber.contains(".")) {
            currentNumber += "."
        }
        updateDisplay(tvResult, tvInput)
    }

    private fun setOperation(op: String, tvResult: TextView, tvInput: TextView) {
        if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && operator.isNotEmpty()) {
            calculateResult(tvResult, tvInput)
        }

        if (currentNumber.isNotEmpty()) {
            previousNumber = currentNumber
            operator = op
            currentNumber = ""
            tvInput.text = "$previousNumber $operator"
        }
    }

    private fun calculateResult(tvResult: TextView, tvInput: TextView) {
        if (previousNumber.isEmpty() || currentNumber.isEmpty() || operator.isEmpty()) {
            return
        }

        try {
            val num1 = previousNumber.toDouble()
            val num2 = currentNumber.toDouble()

            val result = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "×" -> num1 * num2
                "÷" -> {
                    if (num2 == 0.0) {
                        Toast.makeText(this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show()
                        clear(tvResult, tvInput)
                        return
                    }
                    num1 / num2
                }
                else -> return
            }

            currentNumber = formatResult(result)
            previousNumber = ""
            operator = ""
            isNewOperation = true

            tvInput.text = ""
            tvResult.text = currentNumber
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi tính toán", Toast.LENGTH_SHORT).show()
            clear(tvResult, tvInput)
        }
    }

    private fun clear(tvResult: TextView, tvInput: TextView) {
        currentNumber = ""
        previousNumber = ""
        operator = ""
        isNewOperation = true
        tvResult.text = "0"
        tvInput.text = ""
    }

    private fun toggleSign(tvResult: TextView, tvInput: TextView) {
        if (currentNumber.isEmpty() || currentNumber == "0") return

        currentNumber = if (currentNumber.startsWith("-")) {
            currentNumber.substring(1)
        } else {
            "-$currentNumber"
        }
        updateDisplay(tvResult, tvInput)
    }

    private fun applyPercent(tvResult: TextView, tvInput: TextView) {
        if (currentNumber.isEmpty()) return

        try {
            val num = currentNumber.toDouble()
            currentNumber = formatResult(num / 100)
            updateDisplay(tvResult, tvInput)
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun updateDisplay(tvResult: TextView, tvInput: TextView) {
        tvResult.text = if (currentNumber.isEmpty()) "0" else currentNumber

        if (previousNumber.isNotEmpty() && operator.isNotEmpty()) {
            tvInput.text = "$previousNumber $operator"
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            String.format("%.8f", value).trimEnd('0').trimEnd('.')
        }
    }
}


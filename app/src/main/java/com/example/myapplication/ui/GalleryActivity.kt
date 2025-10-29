package com.example.myapplication.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class GalleryActivity : AppCompatActivity() {
    private val images = intArrayOf(
        android.R.drawable.ic_menu_camera,
        android.R.drawable.ic_menu_gallery,
        android.R.drawable.ic_menu_info_details,
        android.R.drawable.ic_menu_preferences,
        android.R.drawable.ic_menu_search,
        android.R.drawable.ic_menu_share,
        android.R.drawable.ic_menu_compass,
        android.R.drawable.ic_menu_call,
        android.R.drawable.ic_menu_add
    )

    private var currentPosition = 0
    private var isSlideshow = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var imageSwitcher: ImageSwitcher
    private lateinit var btnSlideshow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        imageSwitcher = findViewById(R.id.mainImageView)
        val gridView: GridView = findViewById(R.id.gridView)
        val btnPrevious: Button = findViewById(R.id.btnPrevious)
        val btnNext: Button = findViewById(R.id.btnNext)
        btnSlideshow = findViewById(R.id.btnSlideshow)
        val tvImageCount: TextView = findViewById(R.id.tvImageCount)

        // Setup ImageSwitcher with animation
        imageSwitcher.setFactory {
            ImageView(this).apply {
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
        }

        val inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        val outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        imageSwitcher.inAnimation = inAnimation
        imageSwitcher.outAnimation = outAnimation

        val adapter = ImageAdapter(this, images)
        gridView.adapter = adapter

        tvImageCount.text = "${images.size} ảnh"

        gridView.setOnItemClickListener { _, _, position, _ ->
            currentPosition = position
            imageSwitcher.setImageResource(images[position])
            stopSlideshow()
        }

        btnPrevious.setOnClickListener {
            showPreviousImage()
            stopSlideshow()
        }

        btnNext.setOnClickListener {
            showNextImage()
            stopSlideshow()
        }

        btnSlideshow.setOnClickListener {
            toggleSlideshow()
        }

        imageSwitcher.setImageResource(images[0])
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
            .setMessage("Ứng dụng Thư viện Ảnh\n\n" +
                    "Phiên bản: 1.0\n" +
                    "Ứng dụng quản lý và xem ảnh.\n\n" +
                    "Chức năng:\n" +
                    "- Xem ảnh dạng lưới\n" +
                    "- Xem ảnh toàn màn hình\n" +
                    "- Chuyển ảnh Previous/Next\n" +
                    "- Slideshow tự động\n\n" +
                    "Phát triển bởi: Sinh viên CNTT")
            .setPositiveButton("Đóng", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }

    private fun showPreviousImage() {
        currentPosition = if (currentPosition > 0) currentPosition - 1 else images.size - 1
        imageSwitcher.setImageResource(images[currentPosition])
    }

    private fun showNextImage() {
        currentPosition = (currentPosition + 1) % images.size
        imageSwitcher.setImageResource(images[currentPosition])
    }

    private fun toggleSlideshow() {
        if (isSlideshow) {
            stopSlideshow()
        } else {
            startSlideshow()
        }
    }

    private fun startSlideshow() {
        isSlideshow = true
        btnSlideshow.text = "⏸ Dừng"
        Toast.makeText(this, "Bắt đầu slideshow", Toast.LENGTH_SHORT).show()
        runSlideshow()
    }

    private fun stopSlideshow() {
        isSlideshow = false
        btnSlideshow.text = "▶ Slideshow"
        handler.removeCallbacksAndMessages(null)
    }

    private fun runSlideshow() {
        handler.postDelayed({
            if (isSlideshow) {
                showNextImage()
                runSlideshow()
            }
        }, 2000) // Change image every 2 seconds
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}


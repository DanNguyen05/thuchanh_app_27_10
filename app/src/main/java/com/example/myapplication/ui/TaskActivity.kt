package com.example.myapplication.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Simple TaskItem
data class TaskItem(
    val title: String,
    val description: String,
    val deadline: String,
    var isCompleted: Boolean = false
)

class TaskActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var tvTaskCount: TextView
    private val tasks = mutableListOf<TaskItem>()
    private lateinit var adapter: TaskAdapter
    private val PREFS_NAME = "TaskPrefs"
    private val TASKS_KEY = "tasks"
    private val CHANNEL_ID = "task_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        taskRecyclerView = findViewById(R.id.taskListView)
        tvTaskCount = findViewById(R.id.tvTaskCount)

        // Setup RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks, ::onTaskClick, ::onTaskDelete)
        taskRecyclerView.adapter = adapter

        // Load saved tasks
        loadTasks()
        updateTaskCount()

        // Create notification channel
        createNotificationChannel()

        findViewById<Button>(R.id.btnAddTask).setOnClickListener {
            showAddTaskDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 1, 0, "Xóa tất cả")
        menu?.add(0, 2, 0, "Giới thiệu ứng dụng")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            1 -> {
                deleteAllTasks()
                true
            }
            2 -> {
                showAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadTasks() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val tasksJson = prefs.getString(TASKS_KEY, null)
        if (tasksJson != null) {
            val type = object : TypeToken<List<TaskItem>>() {}.type
            val loadedTasks: List<TaskItem> = Gson().fromJson(tasksJson, type)
            tasks.clear()
            tasks.addAll(loadedTasks)
            adapter.notifyDataSetChanged()
        }
    }

    private fun saveTasks() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val tasksJson = Gson().toJson(tasks)
        editor.putString(TASKS_KEY, tasksJson)
        editor.apply()
    }

    private fun updateTaskCount() {
        tvTaskCount.text = "${tasks.size} công việc"
    }

    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTaskTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etTaskDescription)
        val etDeadline = dialogView.findViewById<EditText>(R.id.etTaskDeadline)

        AlertDialog.Builder(this)
            .setTitle("Thêm công việc mới")
            .setView(dialogView)
            .setPositiveButton("Lưu") { _, _ ->
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                val deadline = etDeadline.text.toString().ifEmpty { "Không có" }

                if (title.isNotEmpty()) {
                    val newTask = TaskItem(title, description, deadline, false)
                    tasks.add(newTask)
                    adapter.notifyItemInserted(tasks.size - 1)
                    saveTasks()
                    updateTaskCount()

                    // Show notification
                    showNotification("Đã thêm công việc", title)
                } else {
                    Toast.makeText(this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun onTaskClick(position: Int) {
        val task = tasks[position]
        task.isCompleted = !task.isCompleted
        adapter.notifyItemChanged(position)
        saveTasks()

        val message = if (task.isCompleted) "Đã hoàn thành" else "Chưa hoàn thành"
        Toast.makeText(this, "$message: ${task.title}", Toast.LENGTH_SHORT).show()
    }

    private fun onTaskDelete(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa công việc này?")
            .setPositiveButton("Xóa") { _, _ ->
                tasks.removeAt(position)
                adapter.notifyItemRemoved(position)
                saveTasks()
                updateTaskCount()
                Toast.makeText(this, "Đã xóa công việc", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun deleteAllTasks() {
        AlertDialog.Builder(this)
            .setTitle("Xóa tất cả công việc")
            .setMessage("Bạn có chắc muốn xóa tất cả công việc?")
            .setPositiveButton("Xóa") { _, _ ->
                tasks.clear()
                adapter.notifyDataSetChanged()
                saveTasks()
                updateTaskCount()
                Toast.makeText(this, "Đã xóa tất cả công việc", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Giới thiệu ứng dụng")
            .setMessage("Ứng dụng S-Task\n\n" +
                    "Phiên bản: 1.0\n" +
                    "Ứng dụng quản lý công việc cá nhân.\n\n" +
                    "Chức năng:\n" +
                    "- Thêm công việc mới\n" +
                    "- Đánh dấu hoàn thành\n" +
                    "- Xóa công việc\n" +
                    "- Lưu trữ tự động\n\n" +
                    "Phát triển bởi: Sinh viên CNTT")
            .setPositiveButton("Đóng", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Task Notifications"
            val descriptionText = "Thông báo về công việc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, content: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }
}

// TaskAdapter
class TaskAdapter(
    private val tasks: List<TaskItem>,
    private val onTaskClick: (Int) -> Unit,
    private val onTaskDelete: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvDesc: TextView = itemView.findViewById(R.id.tvTaskDescription)
        val tvCheckbox: TextView = itemView.findViewById(R.id.tvCheckbox)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteTask)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): TaskViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        
        holder.tvTitle.text = task.title
        holder.tvDesc.text = "${task.description}\n📅 Hạn: ${task.deadline}"
        
        // Update checkbox icon
        holder.tvCheckbox.text = if (task.isCompleted) "✅" else "⭕"
        
        // Apply strikethrough if completed
        if (task.isCompleted) {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvTitle.alpha = 0.6f
            holder.tvDesc.alpha = 0.6f
        } else {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tvTitle.alpha = 1.0f
            holder.tvDesc.alpha = 1.0f
        }
        
        // Click handlers
        holder.tvCheckbox.setOnClickListener { onTaskClick(position) }
        holder.itemView.setOnClickListener { onTaskClick(position) }
        holder.btnDelete.setOnClickListener { onTaskDelete(position) }
    }

    override fun getItemCount() = tasks.size
}

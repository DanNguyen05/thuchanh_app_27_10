package com.example.myapplication.data

import androidx.compose.runtime.Immutable

@Immutable
data class Note(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val content: String
)

// Data models for Learning App
@Immutable
data class Course(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val description: String,
    val iconEmoji: String,
    val color: Long,
    val totalLessons: Int,
    val completedLessons: Int = 0
)

@Immutable
data class Lesson(
    val id: Long = System.currentTimeMillis(),
    val courseId: Long,
    val title: String,
    val content: String,
    val order: Int,
    val isCompleted: Boolean = false,
    val videoUrl: String? = null
)

@Immutable
data class Quiz(
    val id: Long = System.currentTimeMillis(),
    val lessonId: Long,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

@Immutable
data class UserProgress(
    val userId: String = "default_user",
    val completedLessons: Set<Long> = emptySet(),
    val quizScores: Map<Long, Int> = emptyMap()
)

// Contact model for danh bạ
@Immutable
data class Contact(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val phone: String,
    val email: String? = null,
    val address: String? = null
)

// Message model for tin nhắn
@Immutable
data class Message(
    val id: Long = System.currentTimeMillis(),
    val contactId: Long,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByMe: Boolean = true
)


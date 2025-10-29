package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.data.database.LearningDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LearningRepository(context: Context) {
    private val dao = LearningDao(context)

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _lessons = MutableStateFlow<List<Lesson>>(emptyList())
    val lessons: StateFlow<List<Lesson>> = _lessons.asStateFlow()

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private val _userProgress = MutableStateFlow(UserProgress())
    val userProgress: StateFlow<UserProgress> = _userProgress.asStateFlow()

    init {
        // Load data from database
        refreshData()
    }

    private fun refreshData() {
        _courses.value = dao.getAllCourses()
        _lessons.value = dao.getAllLessons()
        _contacts.value = dao.getAllContacts()
    }

    // Courses / Lessons / Quizzes (existing methods)
    fun getCourseById(id: Long): Course? {
        return dao.getCourseById(id)
    }

    fun getLessonsByCourseId(courseId: Long): List<Lesson> {
        return dao.getLessonsByCourseId(courseId)
    }

    fun getLessonById(id: Long): Lesson? {
        return dao.getLessonById(id)
    }

    fun getQuizzesByLessonId(lessonId: Long): List<Quiz> {
        return dao.getQuizzesByLessonId(lessonId)
    }

    fun markLessonComplete(lessonId: Long) {
        dao.markLessonComplete(lessonId)
        dao.saveCompletedLesson("default_user", lessonId)
        refreshData()
    }

    fun saveQuizScore(quizId: Long, score: Int) {
        dao.saveQuizScore("default_user", quizId, score)
    }

    fun addCourse(course: Course): Long {
        val result = dao.addCourse(course)
        refreshData()
        return result
    }

    fun addLesson(lesson: Lesson): Long {
        val result = dao.addLesson(lesson)
        refreshData()
        return result
    }

    fun addQuiz(quiz: Quiz): Long {
        return dao.addQuiz(quiz)
    }

    // ============ CONTACTS ============
    fun getAllContacts(): List<Contact> {
        return dao.getAllContacts()
    }

    fun getContactById(id: Long): Contact? {
        return dao.getContactById(id)
    }

    fun addContact(contact: Contact): Long {
        val result = dao.addContact(contact)
        refreshData()
        return result
    }

    fun updateContact(contact: Contact): Int {
        val result = dao.updateContact(contact)
        refreshData()
        return result
    }

    fun deleteContact(contactId: Long): Int {
        val result = dao.deleteContact(contactId)
        refreshData()
        return result
    }

    // ============ MESSAGES ============
    fun getMessagesByContactId(contactId: Long): List<Message> {
        return dao.getMessagesByContactId(contactId)
    }

    fun addMessage(message: Message): Long {
        return dao.addMessage(message)
    }

    fun deleteMessage(messageId: Long): Int {
        return dao.deleteMessage(messageId)
    }
}

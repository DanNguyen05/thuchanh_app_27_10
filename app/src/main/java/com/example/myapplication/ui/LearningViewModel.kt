package com.example.myapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.Course
import com.example.myapplication.data.LearningRepository
import com.example.myapplication.data.Lesson
import com.example.myapplication.data.Quiz
import com.example.myapplication.data.Contact
import com.example.myapplication.data.Message
import kotlinx.coroutines.flow.StateFlow

class LearningViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LearningRepository(application.applicationContext)

    val courses: StateFlow<List<Course>> = repository.courses
    val lessons: StateFlow<List<Lesson>> = repository.lessons
    val contacts: StateFlow<List<Contact>> = repository.contacts
    val userProgress = repository.userProgress

    fun getCourseById(id: Long): Course? {
        return repository.getCourseById(id)
    }

    fun getLessonsByCourseId(courseId: Long): List<Lesson> {
        return repository.getLessonsByCourseId(courseId)
    }

    fun getLessonById(id: Long): Lesson? {
        return repository.getLessonById(id)
    }

    fun getQuizzesByLessonId(lessonId: Long): List<Quiz> {
        return repository.getQuizzesByLessonId(lessonId)
    }

    fun markLessonComplete(lessonId: Long) {
        repository.markLessonComplete(lessonId)
    }

    fun saveQuizScore(quizId: Long, score: Int) {
        repository.saveQuizScore(quizId, score)
    }

    // Contacts
    fun getAllContacts(): List<Contact> = repository.getAllContacts()
    fun getContactById(id: Long): Contact? = repository.getContactById(id)
    fun addContact(contact: Contact): Long = repository.addContact(contact)
    fun updateContact(contact: Contact): Int = repository.updateContact(contact)
    fun deleteContact(contactId: Long): Int = repository.deleteContact(contactId)

    // Messages
    fun getMessagesByContactId(contactId: Long): List<Message> = repository.getMessagesByContactId(contactId)
    fun addMessage(message: Message): Long = repository.addMessage(message)
    fun deleteMessage(messageId: Long): Int = repository.deleteMessage(messageId)
}

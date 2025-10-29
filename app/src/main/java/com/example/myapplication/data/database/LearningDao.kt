package com.example.myapplication.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.myapplication.data.Course
import com.example.myapplication.data.Lesson
import com.example.myapplication.data.Quiz
import com.example.myapplication.data.Contact
import com.example.myapplication.data.Message

class LearningDao(context: Context) {
    private val dbHelper = LearningDbHelper(context)

    // ============ COURSE OPERATIONS ============

    fun getAllCourses(): List<Course> {
        val db = dbHelper.readableDatabase
        val courses = mutableListOf<Course>()

        val cursor = db.query(
            DatabaseContract.CourseEntry.TABLE_NAME,
            null, null, null, null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val course = Course(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_ID)),
                    title = getString(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_TITLE)),
                    description = getString(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_DESCRIPTION)),
                    iconEmoji = getString(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_ICON_EMOJI)),
                    color = getLong(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_COLOR)),
                    totalLessons = getInt(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_TOTAL_LESSONS)),
                    completedLessons = getInt(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_COMPLETED_LESSONS))
                )
                courses.add(course)
            }
        }
        cursor.close()
        return courses
    }

    fun getCourseById(id: Long): Course? {
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            DatabaseContract.CourseEntry.TABLE_NAME,
            null,
            "${DatabaseContract.CourseEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        var course: Course? = null
        with(cursor) {
            if (moveToFirst()) {
                course = Course(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_ID)),
                    title = getString(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_TITLE)),
                    description = getString(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_DESCRIPTION)),
                    iconEmoji = getString(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_ICON_EMOJI)),
                    color = getLong(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_COLOR)),
                    totalLessons = getInt(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_TOTAL_LESSONS)),
                    completedLessons = getInt(getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_COMPLETED_LESSONS))
                )
            }
        }
        cursor.close()
        return course
    }

    fun addCourse(course: Course): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.CourseEntry.COLUMN_ID, course.id)
            put(DatabaseContract.CourseEntry.COLUMN_TITLE, course.title)
            put(DatabaseContract.CourseEntry.COLUMN_DESCRIPTION, course.description)
            put(DatabaseContract.CourseEntry.COLUMN_ICON_EMOJI, course.iconEmoji)
            put(DatabaseContract.CourseEntry.COLUMN_COLOR, course.color)
            put(DatabaseContract.CourseEntry.COLUMN_TOTAL_LESSONS, course.totalLessons)
            put(DatabaseContract.CourseEntry.COLUMN_COMPLETED_LESSONS, course.completedLessons)
        }

        return db.insert(DatabaseContract.CourseEntry.TABLE_NAME, null, values)
    }

    fun updateCourse(course: Course): Int {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.CourseEntry.COLUMN_TITLE, course.title)
            put(DatabaseContract.CourseEntry.COLUMN_DESCRIPTION, course.description)
            put(DatabaseContract.CourseEntry.COLUMN_ICON_EMOJI, course.iconEmoji)
            put(DatabaseContract.CourseEntry.COLUMN_COLOR, course.color)
            put(DatabaseContract.CourseEntry.COLUMN_TOTAL_LESSONS, course.totalLessons)
            put(DatabaseContract.CourseEntry.COLUMN_COMPLETED_LESSONS, course.completedLessons)
        }

        return db.update(
            DatabaseContract.CourseEntry.TABLE_NAME,
            values,
            "${DatabaseContract.CourseEntry.COLUMN_ID} = ?",
            arrayOf(course.id.toString())
        )
    }

    fun updateCourseProgress(courseId: Long, completedLessons: Int): Int {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.CourseEntry.COLUMN_COMPLETED_LESSONS, completedLessons)
        }

        return db.update(
            DatabaseContract.CourseEntry.TABLE_NAME,
            values,
            "${DatabaseContract.CourseEntry.COLUMN_ID} = ?",
            arrayOf(courseId.toString())
        )
    }

    // ============ LESSON OPERATIONS ============

    fun getAllLessons(): List<Lesson> {
        val db = dbHelper.readableDatabase
        val lessons = mutableListOf<Lesson>()

        val cursor = db.query(
            DatabaseContract.LessonEntry.TABLE_NAME,
            null, null, null, null, null,
            DatabaseContract.LessonEntry.COLUMN_ORDER
        )

        with(cursor) {
            while (moveToNext()) {
                val lesson = Lesson(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_ID)),
                    courseId = getLong(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_COURSE_ID)),
                    title = getString(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_TITLE)),
                    content = getString(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_CONTENT)),
                    order = getInt(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_ORDER)),
                    isCompleted = getInt(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_IS_COMPLETED)) == 1,
                    videoUrl = getStringOrNull(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_VIDEO_URL))
                )
                lessons.add(lesson)
            }
        }
        cursor.close()
        return lessons
    }

    fun getLessonsByCourseId(courseId: Long): List<Lesson> {
        val db = dbHelper.readableDatabase
        val lessons = mutableListOf<Lesson>()

        val cursor = db.query(
            DatabaseContract.LessonEntry.TABLE_NAME,
            null,
            "${DatabaseContract.LessonEntry.COLUMN_COURSE_ID} = ?",
            arrayOf(courseId.toString()),
            null, null,
            DatabaseContract.LessonEntry.COLUMN_ORDER
        )

        with(cursor) {
            while (moveToNext()) {
                val lesson = Lesson(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_ID)),
                    courseId = getLong(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_COURSE_ID)),
                    title = getString(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_TITLE)),
                    content = getString(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_CONTENT)),
                    order = getInt(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_ORDER)),
                    isCompleted = getInt(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_IS_COMPLETED)) == 1,
                    videoUrl = getStringOrNull(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_VIDEO_URL))
                )
                lessons.add(lesson)
            }
        }
        cursor.close()
        return lessons
    }

    fun getLessonById(id: Long): Lesson? {
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            DatabaseContract.LessonEntry.TABLE_NAME,
            null,
            "${DatabaseContract.LessonEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        var lesson: Lesson? = null
        with(cursor) {
            if (moveToFirst()) {
                lesson = Lesson(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_ID)),
                    courseId = getLong(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_COURSE_ID)),
                    title = getString(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_TITLE)),
                    content = getString(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_CONTENT)),
                    order = getInt(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_ORDER)),
                    isCompleted = getInt(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_IS_COMPLETED)) == 1,
                    videoUrl = getStringOrNull(getColumnIndexOrThrow(DatabaseContract.LessonEntry.COLUMN_VIDEO_URL))
                )
            }
        }
        cursor.close()
        return lesson
    }

    fun addLesson(lesson: Lesson): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.LessonEntry.COLUMN_ID, lesson.id)
            put(DatabaseContract.LessonEntry.COLUMN_COURSE_ID, lesson.courseId)
            put(DatabaseContract.LessonEntry.COLUMN_TITLE, lesson.title)
            put(DatabaseContract.LessonEntry.COLUMN_CONTENT, lesson.content)
            put(DatabaseContract.LessonEntry.COLUMN_ORDER, lesson.order)
            put(DatabaseContract.LessonEntry.COLUMN_IS_COMPLETED, if (lesson.isCompleted) 1 else 0)
            put(DatabaseContract.LessonEntry.COLUMN_VIDEO_URL, lesson.videoUrl)
        }

        return db.insert(DatabaseContract.LessonEntry.TABLE_NAME, null, values)
    }

    fun markLessonComplete(lessonId: Long): Int {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.LessonEntry.COLUMN_IS_COMPLETED, 1)
        }

        val result = db.update(
            DatabaseContract.LessonEntry.TABLE_NAME,
            values,
            "${DatabaseContract.LessonEntry.COLUMN_ID} = ?",
            arrayOf(lessonId.toString())
        )

        // Update course progress
        val lesson = getLessonById(lessonId)
        lesson?.let {
            val completedCount = getCompletedLessonsCount(it.courseId)
            updateCourseProgress(it.courseId, completedCount)
        }

        return result
    }

    private fun getCompletedLessonsCount(courseId: Long): Int {
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${DatabaseContract.LessonEntry.TABLE_NAME} " +
                    "WHERE ${DatabaseContract.LessonEntry.COLUMN_COURSE_ID} = ? " +
                    "AND ${DatabaseContract.LessonEntry.COLUMN_IS_COMPLETED} = 1",
            arrayOf(courseId.toString())
        )

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    // ============ QUIZ OPERATIONS ============

    fun getQuizzesByLessonId(lessonId: Long): List<Quiz> {
        val db = dbHelper.readableDatabase
        val quizzes = mutableListOf<Quiz>()

        val cursor = db.query(
            DatabaseContract.QuizEntry.TABLE_NAME,
            null,
            "${DatabaseContract.QuizEntry.COLUMN_LESSON_ID} = ?",
            arrayOf(lessonId.toString()),
            null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val optionsString = getString(getColumnIndexOrThrow(DatabaseContract.QuizEntry.COLUMN_OPTIONS))
                val options = optionsString.split("|")

                val quiz = Quiz(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.QuizEntry.COLUMN_ID)),
                    lessonId = getLong(getColumnIndexOrThrow(DatabaseContract.QuizEntry.COLUMN_LESSON_ID)),
                    question = getString(getColumnIndexOrThrow(DatabaseContract.QuizEntry.COLUMN_QUESTION)),
                    options = options,
                    correctAnswerIndex = getInt(getColumnIndexOrThrow(DatabaseContract.QuizEntry.COLUMN_CORRECT_ANSWER_INDEX)),
                    explanation = getString(getColumnIndexOrThrow(DatabaseContract.QuizEntry.COLUMN_EXPLANATION))
                )
                quizzes.add(quiz)
            }
        }
        cursor.close()
        return quizzes
    }

    fun addQuiz(quiz: Quiz): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.QuizEntry.COLUMN_ID, quiz.id)
            put(DatabaseContract.QuizEntry.COLUMN_LESSON_ID, quiz.lessonId)
            put(DatabaseContract.QuizEntry.COLUMN_QUESTION, quiz.question)
            put(DatabaseContract.QuizEntry.COLUMN_OPTIONS, quiz.options.joinToString("|"))
            put(DatabaseContract.QuizEntry.COLUMN_CORRECT_ANSWER_INDEX, quiz.correctAnswerIndex)
            put(DatabaseContract.QuizEntry.COLUMN_EXPLANATION, quiz.explanation)
        }

        return db.insert(DatabaseContract.QuizEntry.TABLE_NAME, null, values)
    }

    // ============ PROGRESS OPERATIONS ============

    fun saveQuizScore(userId: String, quizId: Long, score: Int): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.ProgressEntry.COLUMN_USER_ID, userId)
            put(DatabaseContract.ProgressEntry.COLUMN_QUIZ_ID, quizId)
            put(DatabaseContract.ProgressEntry.COLUMN_SCORE, score)
            put(DatabaseContract.ProgressEntry.COLUMN_COMPLETED_DATE, System.currentTimeMillis())
        }

        return db.insert(DatabaseContract.ProgressEntry.TABLE_NAME, null, values)
    }

    fun saveCompletedLesson(userId: String, lessonId: Long): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.ProgressEntry.COLUMN_USER_ID, userId)
            put(DatabaseContract.ProgressEntry.COLUMN_LESSON_ID, lessonId)
            put(DatabaseContract.ProgressEntry.COLUMN_COMPLETED_DATE, System.currentTimeMillis())
        }

        return db.insert(DatabaseContract.ProgressEntry.TABLE_NAME, null, values)
    }

    // ============ CONTACTS (DANH BẠ) OPERATIONS ============

    fun getAllContacts(): List<Contact> {
        val db = dbHelper.readableDatabase
        val contacts = mutableListOf<Contact>()

        val cursor = db.query(
            DatabaseContract.ContactEntry.TABLE_NAME,
            null, null, null, null, null,
            DatabaseContract.ContactEntry.COLUMN_NAME
        )

        with(cursor) {
            while (moveToNext()) {
                val contact = Contact(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_NAME)),
                    phone = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_PHONE)),
                    email = getStringOrNull(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_EMAIL)),
                    address = getStringOrNull(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_ADDRESS))
                )
                contacts.add(contact)
            }
        }
        cursor.close()
        return contacts
    }

    fun getContactById(id: Long): Contact? {
        val db = dbHelper.readableDatabase
        var contact: Contact? = null

        val cursor = db.query(
            DatabaseContract.ContactEntry.TABLE_NAME,
            null,
            "${DatabaseContract.ContactEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        with(cursor) {
            if (moveToFirst()) {
                contact = Contact(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_NAME)),
                    phone = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_PHONE)),
                    email = getStringOrNull(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_EMAIL)),
                    address = getStringOrNull(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_ADDRESS))
                )
            }
        }
        cursor.close()
        return contact
    }

    fun addContact(contact: Contact): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.ContactEntry.COLUMN_ID, contact.id)
            put(DatabaseContract.ContactEntry.COLUMN_NAME, contact.name)
            put(DatabaseContract.ContactEntry.COLUMN_PHONE, contact.phone)
            put(DatabaseContract.ContactEntry.COLUMN_EMAIL, contact.email)
            put(DatabaseContract.ContactEntry.COLUMN_ADDRESS, contact.address)
        }
        return db.insert(DatabaseContract.ContactEntry.TABLE_NAME, null, values)
    }

    fun updateContact(contact: Contact): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.ContactEntry.COLUMN_NAME, contact.name)
            put(DatabaseContract.ContactEntry.COLUMN_PHONE, contact.phone)
            put(DatabaseContract.ContactEntry.COLUMN_EMAIL, contact.email)
            put(DatabaseContract.ContactEntry.COLUMN_ADDRESS, contact.address)
        }
        return db.update(
            DatabaseContract.ContactEntry.TABLE_NAME,
            values,
            "${DatabaseContract.ContactEntry.COLUMN_ID} = ?",
            arrayOf(contact.id.toString())
        )
    }

    fun deleteContact(contactId: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseContract.ContactEntry.TABLE_NAME,
            "${DatabaseContract.ContactEntry.COLUMN_ID} = ?",
            arrayOf(contactId.toString())
        )
    }

    // ============ MESSAGE OPERATIONS ============

    fun getMessagesByContactId(contactId: Long): List<Message> {
        val db = dbHelper.readableDatabase
        val messages = mutableListOf<Message>()

        val cursor = db.query(
            DatabaseContract.MessageEntry.TABLE_NAME,
            null,
            "${DatabaseContract.MessageEntry.COLUMN_CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null,
            null,
            "${DatabaseContract.MessageEntry.COLUMN_TIMESTAMP} ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val message = Message(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.MessageEntry.COLUMN_ID)),
                    contactId = getLong(getColumnIndexOrThrow(DatabaseContract.MessageEntry.COLUMN_CONTACT_ID)),
                    content = getString(getColumnIndexOrThrow(DatabaseContract.MessageEntry.COLUMN_CONTENT)),
                    timestamp = getLong(getColumnIndexOrThrow(DatabaseContract.MessageEntry.COLUMN_TIMESTAMP)),
                    isSentByMe = getInt(getColumnIndexOrThrow(DatabaseContract.MessageEntry.COLUMN_IS_SENT_BY_ME)) == 1
                )
                messages.add(message)
            }
        }
        cursor.close()
        return messages
    }

    fun addMessage(message: Message): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.MessageEntry.COLUMN_CONTACT_ID, message.contactId)
            put(DatabaseContract.MessageEntry.COLUMN_CONTENT, message.content)
            put(DatabaseContract.MessageEntry.COLUMN_TIMESTAMP, message.timestamp)
            put(DatabaseContract.MessageEntry.COLUMN_IS_SENT_BY_ME, if (message.isSentByMe) 1 else 0)
        }
        return db.insert(DatabaseContract.MessageEntry.TABLE_NAME, null, values)
    }

    fun deleteMessage(messageId: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseContract.MessageEntry.TABLE_NAME,
            "${DatabaseContract.MessageEntry.COLUMN_ID} = ?",
            arrayOf(messageId.toString())
        )
    }

    // Helper function
    private fun Cursor.getStringOrNull(columnIndex: Int): String? {
        return if (isNull(columnIndex)) null else getString(columnIndex)
    }

    fun close() {
        dbHelper.close()
    }
}

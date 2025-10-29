package com.example.myapplication.data.database

import android.provider.BaseColumns

object DatabaseContract {

    // Course table
    object CourseEntry : BaseColumns {
        const val TABLE_NAME = "courses"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ICON_EMOJI = "icon_emoji"
        const val COLUMN_COLOR = "color"
        const val COLUMN_TOTAL_LESSONS = "total_lessons"
        const val COLUMN_COMPLETED_LESSONS = "completed_lessons"
    }

    // Lesson table
    object LessonEntry : BaseColumns {
        const val TABLE_NAME = "lessons"
        const val COLUMN_ID = "id"
        const val COLUMN_COURSE_ID = "course_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_ORDER = "lesson_order"
        const val COLUMN_IS_COMPLETED = "is_completed"
        const val COLUMN_VIDEO_URL = "video_url"
    }

    // Quiz table
    object QuizEntry : BaseColumns {
        const val TABLE_NAME = "quizzes"
        const val COLUMN_ID = "id"
        const val COLUMN_LESSON_ID = "lesson_id"
        const val COLUMN_QUESTION = "question"
        const val COLUMN_OPTIONS = "options"
        const val COLUMN_CORRECT_ANSWER_INDEX = "correct_answer_index"
        const val COLUMN_EXPLANATION = "explanation"
    }

    // User Progress table
    object ProgressEntry : BaseColumns {
        const val TABLE_NAME = "user_progress"
        const val COLUMN_ID = "_id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_LESSON_ID = "lesson_id"
        const val COLUMN_QUIZ_ID = "quiz_id"
        const val COLUMN_SCORE = "score"
        const val COLUMN_COMPLETED_DATE = "completed_date"
    }

    // Contact table (Danh bạ)
    object ContactEntry : BaseColumns {
        const val TABLE_NAME = "contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_ADDRESS = "address"
    }

    // Message table (Tin nhắn)
    object MessageEntry : BaseColumns {
        const val TABLE_NAME = "messages"
        const val COLUMN_ID = "id"
        const val COLUMN_CONTACT_ID = "contact_id"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_IS_SENT_BY_ME = "is_sent_by_me"
    }
}

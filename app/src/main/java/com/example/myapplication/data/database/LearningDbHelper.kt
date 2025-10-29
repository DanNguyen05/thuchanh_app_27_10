package com.example.myapplication.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LearningDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "Learning.db"
    }

    // SQL statements for creating tables
    private val SQL_CREATE_COURSES =
        "CREATE TABLE ${DatabaseContract.CourseEntry.TABLE_NAME} (" +
                "${DatabaseContract.CourseEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${DatabaseContract.CourseEntry.COLUMN_TITLE} TEXT," +
                "${DatabaseContract.CourseEntry.COLUMN_DESCRIPTION} TEXT," +
                "${DatabaseContract.CourseEntry.COLUMN_ICON_EMOJI} TEXT," +
                "${DatabaseContract.CourseEntry.COLUMN_COLOR} INTEGER," +
                "${DatabaseContract.CourseEntry.COLUMN_TOTAL_LESSONS} INTEGER," +
                "${DatabaseContract.CourseEntry.COLUMN_COMPLETED_LESSONS} INTEGER DEFAULT 0)"

    private val SQL_CREATE_LESSONS =
        "CREATE TABLE ${DatabaseContract.LessonEntry.TABLE_NAME} (" +
                "${DatabaseContract.LessonEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${DatabaseContract.LessonEntry.COLUMN_COURSE_ID} INTEGER," +
                "${DatabaseContract.LessonEntry.COLUMN_TITLE} TEXT," +
                "${DatabaseContract.LessonEntry.COLUMN_CONTENT} TEXT," +
                "${DatabaseContract.LessonEntry.COLUMN_ORDER} INTEGER," +
                "${DatabaseContract.LessonEntry.COLUMN_IS_COMPLETED} INTEGER DEFAULT 0," +
                "${DatabaseContract.LessonEntry.COLUMN_VIDEO_URL} TEXT," +
                "FOREIGN KEY(${DatabaseContract.LessonEntry.COLUMN_COURSE_ID}) " +
                "REFERENCES ${DatabaseContract.CourseEntry.TABLE_NAME}(${DatabaseContract.CourseEntry.COLUMN_ID}))"

    private val SQL_CREATE_QUIZZES =
        "CREATE TABLE ${DatabaseContract.QuizEntry.TABLE_NAME} (" +
                "${DatabaseContract.QuizEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${DatabaseContract.QuizEntry.COLUMN_LESSON_ID} INTEGER," +
                "${DatabaseContract.QuizEntry.COLUMN_QUESTION} TEXT," +
                "${DatabaseContract.QuizEntry.COLUMN_OPTIONS} TEXT," +
                "${DatabaseContract.QuizEntry.COLUMN_CORRECT_ANSWER_INDEX} INTEGER," +
                "${DatabaseContract.QuizEntry.COLUMN_EXPLANATION} TEXT," +
                "FOREIGN KEY(${DatabaseContract.QuizEntry.COLUMN_LESSON_ID}) " +
                "REFERENCES ${DatabaseContract.LessonEntry.TABLE_NAME}(${DatabaseContract.LessonEntry.COLUMN_ID}))"

    // Contacts table
    private val SQL_CREATE_CONTACTS =
        "CREATE TABLE ${DatabaseContract.ContactEntry.TABLE_NAME} (" +
                "${DatabaseContract.ContactEntry.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${DatabaseContract.ContactEntry.COLUMN_NAME} TEXT," +
                "${DatabaseContract.ContactEntry.COLUMN_PHONE} TEXT," +
                "${DatabaseContract.ContactEntry.COLUMN_EMAIL} TEXT," +
                "${DatabaseContract.ContactEntry.COLUMN_ADDRESS} TEXT)"

    // Messages table
    private val SQL_CREATE_MESSAGES =
        "CREATE TABLE ${DatabaseContract.MessageEntry.TABLE_NAME} (" +
                "${DatabaseContract.MessageEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DatabaseContract.MessageEntry.COLUMN_CONTACT_ID} INTEGER," +
                "${DatabaseContract.MessageEntry.COLUMN_CONTENT} TEXT," +
                "${DatabaseContract.MessageEntry.COLUMN_TIMESTAMP} INTEGER," +
                "${DatabaseContract.MessageEntry.COLUMN_IS_SENT_BY_ME} INTEGER," +
                "FOREIGN KEY(${DatabaseContract.MessageEntry.COLUMN_CONTACT_ID}) REFERENCES ${DatabaseContract.ContactEntry.TABLE_NAME}(${DatabaseContract.ContactEntry.COLUMN_ID})" +
                ")"

    private val SQL_CREATE_PROGRESS =
        "CREATE TABLE ${DatabaseContract.ProgressEntry.TABLE_NAME} (" +
                "${DatabaseContract.ProgressEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DatabaseContract.ProgressEntry.COLUMN_USER_ID} TEXT," +
                "${DatabaseContract.ProgressEntry.COLUMN_LESSON_ID} INTEGER," +
                "${DatabaseContract.ProgressEntry.COLUMN_QUIZ_ID} INTEGER," +
                "${DatabaseContract.ProgressEntry.COLUMN_SCORE} INTEGER," +
                "${DatabaseContract.ProgressEntry.COLUMN_COMPLETED_DATE} INTEGER)"

    // SQL statements for deleting tables
    private val SQL_DELETE_COURSES = "DROP TABLE IF EXISTS ${DatabaseContract.CourseEntry.TABLE_NAME}"
    private val SQL_DELETE_LESSONS = "DROP TABLE IF EXISTS ${DatabaseContract.LessonEntry.TABLE_NAME}"
    private val SQL_DELETE_QUIZZES = "DROP TABLE IF EXISTS ${DatabaseContract.QuizEntry.TABLE_NAME}"
    private val SQL_DELETE_CONTACTS = "DROP TABLE IF EXISTS ${DatabaseContract.ContactEntry.TABLE_NAME}"
    private val SQL_DELETE_MESSAGES = "DROP TABLE IF EXISTS ${DatabaseContract.MessageEntry.TABLE_NAME}"
    private val SQL_DELETE_PROGRESS = "DROP TABLE IF EXISTS ${DatabaseContract.ProgressEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_COURSES)
        db.execSQL(SQL_CREATE_LESSONS)
        db.execSQL(SQL_CREATE_CONTACTS)
        db.execSQL(SQL_CREATE_MESSAGES)
        db.execSQL(SQL_CREATE_PROGRESS)

        // Insert sample data
        insertSampleData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_PROGRESS)
        db.execSQL(SQL_DELETE_MESSAGES)
        db.execSQL(SQL_DELETE_QUIZZES)
        db.execSQL(SQL_DELETE_LESSONS)
        db.execSQL(SQL_DELETE_COURSES)
        db.execSQL(SQL_DELETE_CONTACTS)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        // Insert sample courses
        db.execSQL("INSERT INTO ${DatabaseContract.CourseEntry.TABLE_NAME} VALUES (1, 'Toán học', 'Học toán từ cơ bản đến nâng cao', '📐', ${0xFF4CAF50}, 9, 0)")
        db.execSQL("INSERT INTO ${DatabaseContract.CourseEntry.TABLE_NAME} VALUES (2, 'Vật lý', 'Khám phá các quy luật vật lý', '⚛️', ${0xFF2196F3}, 4, 0)")
        db.execSQL("INSERT INTO ${DatabaseContract.CourseEntry.TABLE_NAME} VALUES (3, 'Hóa học', 'Tìm hiểu về các phản ứng hóa học', '🧪', ${0xFFFF9800}, 4, 0)")
        db.execSQL("INSERT INTO ${DatabaseContract.CourseEntry.TABLE_NAME} VALUES (4, 'Sinh học', 'Nghiên cứu về sự sống', '🧬', ${0xFF9C27B0}, 4, 0)")
        db.execSQL("INSERT INTO ${DatabaseContract.CourseEntry.TABLE_NAME} VALUES (5, 'Tiếng Anh', 'Nâng cao khả năng tiếng Anh', '🗣️', ${0xFFE91E63}, 5, 0)")
        db.execSQL("INSERT INTO ${DatabaseContract.CourseEntry.TABLE_NAME} VALUES (6, 'Lập trình', 'Học lập trình từ cơ bản', '💻', ${0xFF00BCD4}, 6, 0)")

        // Insert sample lessons - Toán học (9 bài từ Bài 2 đến Bài 10)
        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (102, 1, 'Bài 2: Phân số', " +
                "'# Phân số\\n\\n## 1. Phân số là gì?\\nPhân số là cách biểu diễn một phần của một đơn vị.', " +
                "2, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (103, 1, 'Bài 3: Hình học', " +
                "'# Hình học\\n\\n## 1. Các hình cơ bản\\nHọc về hình vuông, hình tròn, hình tam giác.', " +
                "3, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (104, 1, 'Bài 4: Đại số', " +
                "'# Đại số\\n\\n## 1. Phương trình cơ bản\\nGiải các phương trình bậc nhất.', " +
                "4, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (105, 1, 'Bài 5: Tỷ lệ', " +
                "'# Tỷ lệ và phần trăm\\n\\n## 1. Tính tỷ lệ\\nCách tính tỷ lệ và phần trăm.', " +
                "5, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (106, 1, 'Bài 6: Số thập phân', " +
                "'# Số thập phân\\n\\n## 1. Làm việc với số thập phân\\nCộng trừ nhân chia số thập phân.', " +
                "6, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (107, 1, 'Bài 7: Đo lường', " +
                "'# Đo lường\\n\\n## 1. Các đơn vị đo\\nMét, centimet, kilogram, lít.', " +
                "7, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (108, 1, 'Bài 8: Thống kê', " +
                "'# Thống kê\\n\\n## 1. Biểu đồ và bảng\\nĐọc và vẽ biểu đồ.', " +
                "8, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (109, 1, 'Bài 9: Xác suất', " +
                "'# Xác suất\\n\\n## 1. Xác suất cơ bản\\nTính xác suất của các sự kiện.', " +
                "9, 0, NULL)")

        db.execSQL("INSERT INTO ${DatabaseContract.LessonEntry.TABLE_NAME} VALUES (110, 1, 'Bài 10: Luyện tập tổng hợp', " +
                "'# Luyện tập tổng hợp\\n\\n## 1. Ôn tập toàn bộ\\nÔn tập tất cả kiến thức đã học.', " +
                "10, 0, NULL)")

        // Insert sample quizzes
        db.execSQL("INSERT INTO ${DatabaseContract.QuizEntry.TABLE_NAME} VALUES (1001, 101, " +
                "'Thứ tự thực hiện phép tính nào sau đây là đúng?', " +
                "'Cộng trừ trước, nhân chia sau|Nhân chia trước, cộng trừ sau|Từ trái sang phải|Từ phải sang trái', " +
                "1, 'Thứ tự đúng là: Ngoặc > Nhân chia > Cộng trừ')")

        db.execSQL("INSERT INTO ${DatabaseContract.QuizEntry.TABLE_NAME} VALUES (1002, 101, " +
                "'Kết quả của phép tính (10 + 5) × 2 là?', " +
                "'20|25|30|35', " +
                "2, 'Thực hiện ngoặc trước: 10 + 5 = 15, sau đó nhân: 15 × 2 = 30')")

        // Insert sample contacts
        db.execSQL("INSERT INTO ${DatabaseContract.ContactEntry.TABLE_NAME} VALUES (1, 'Nguyễn Văn A', '0909123456', 'a.nguyen@example.com', 'Hà Nội')")
        db.execSQL("INSERT INTO ${DatabaseContract.ContactEntry.TABLE_NAME} VALUES (2, 'Trần Thị B', '0912345678', 'b.tran@example.com', 'Hồ Chí Minh')")
        db.execSQL("INSERT INTO ${DatabaseContract.ContactEntry.TABLE_NAME} VALUES (3, 'Lê Văn C', '0987654321', NULL, 'Đà Nẵng')")
    }
}

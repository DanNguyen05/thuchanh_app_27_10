package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.CourseDetailScreen
import com.example.myapplication.ui.CourseListScreen
import com.example.myapplication.ui.LessonScreen
import com.example.myapplication.ui.QuizScreen
import com.example.myapplication.ui.ContactListScreen
import com.example.myapplication.ui.ContactDetailScreen
import com.example.myapplication.ui.ChatScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kiểm tra xem có intent mở danh bạ không
        val openContacts = intent.getBooleanExtra("open_contacts", false)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Nếu có intent mở danh bạ, bắt đầu từ màn hình danh bạ
                    val startDestination = if (openContacts) "contact_list" else "course_list"
                    NavHost(navController = navController, startDestination = startDestination) {
                        // Course list screen
                        composable("course_list") {
                            CourseListScreen(
                                onCourseClick = { course ->
                                    navController.navigate("course_detail/${course.id}")
                                },
                                onContactsClick = {
                                    navController.navigate("contact_list")
                                }
                            )
                        }

                        // Course detail screen
                        composable(
                            "course_detail/{courseId}",
                            arguments = listOf(navArgument("courseId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val courseId = backStackEntry.arguments?.getLong("courseId") ?: return@composable
                            CourseDetailScreen(
                                courseId = courseId,
                                onNavigateBack = { navController.popBackStack() },
                                onLessonClick = { lesson ->
                                    navController.navigate("lesson/${lesson.id}")
                                },
                                onContactsClick = {
                                    navController.navigate("contact_list")
                                }
                            )
                        }

                        // Lesson screen
                        composable(
                            "lesson/{lessonId}",
                            arguments = listOf(navArgument("lessonId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val lessonId = backStackEntry.arguments?.getLong("lessonId") ?: return@composable
                            LessonScreen(
                                lessonId = lessonId,
                                onNavigateBack = { navController.popBackStack() },
                                onStartQuiz = { lessonId ->
                                    navController.navigate("quiz/$lessonId")
                                }
                            )
                        }

                        // Quiz screen
                        composable(
                            "quiz/{lessonId}",
                            arguments = listOf(navArgument("lessonId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val lessonId = backStackEntry.arguments?.getLong("lessonId") ?: return@composable
                            QuizScreen(
                                lessonId = lessonId,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        // Contact list screen
                        composable("contact_list") {
                            ContactListScreen(
                                onContactClick = { contact ->
                                    navController.navigate("contact_detail/${contact.id}")
                                },
                                onAddContactClick = {
                                    navController.navigate("contact_detail/-1")
                                },
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        // Contact detail (add/edit)
                        composable(
                            "contact_detail/{contactId}",
                            arguments = listOf(navArgument("contactId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val contactId = backStackEntry.arguments?.getLong("contactId")
                            ContactDetailScreen(
                                contactId = contactId,
                                onNavigateBack = { navController.popBackStack() },
                                onOpenChat = { contactId ->
                                    navController.navigate("chat/$contactId")
                                }
                            )
                        }

                        // Chat screen
                        composable(
                            "chat/{contactId}",
                            arguments = listOf(navArgument("contactId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val contactId = backStackEntry.arguments?.getLong("contactId") ?: return@composable
                            ChatScreen(
                                contactId = contactId,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "course_list") {
                composable("course_list") {
                    CourseListScreen(
                        onCourseClick = {},
                        onContactsClick = {}
                    )
                }
            }
        }
    }
}


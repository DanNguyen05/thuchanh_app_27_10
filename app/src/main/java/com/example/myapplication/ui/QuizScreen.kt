package com.example.myapplication.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Quiz

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    lessonId: Long,
    viewModel: LearningViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val quizzes = viewModel.getQuizzesByLessonId(lessonId)
    val lesson = viewModel.getLessonById(lessonId)
    val course = lesson?.let { viewModel.getCourseById(it.courseId) }

    var currentQuizIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableStateOf(0) }
    var quizCompleted by remember { mutableStateOf(false) }

    val currentQuiz = if (quizzes.isNotEmpty()) quizzes[currentQuizIndex] else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Bài tập ${currentQuizIndex + 1}/${quizzes.size}")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(course?.color ?: 0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (quizCompleted) {
            // Quiz result screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val percentage = (correctAnswers * 100) / quizzes.size
                val emoji = when {
                    percentage >= 80 -> "🎉"
                    percentage >= 60 -> "👍"
                    else -> "💪"
                }

                Text(
                    text = emoji,
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Kết quả bài tập",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(course?.color ?: 0xFF6200EE).copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Điểm số",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "$correctAnswers/${quizzes.size}",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(course?.color ?: 0xFF6200EE)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(course?.color ?: 0xFF6200EE)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(course?.color ?: 0xFF6200EE)
                    )
                ) {
                    Text("Hoàn thành")
                }
            }
        } else {
            // Quiz question screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                currentQuiz?.let { quiz ->
                    // Progress indicator
                    LinearProgressIndicator(
                        progress = { (currentQuizIndex + 1).toFloat() / quizzes.size.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = Color(course?.color ?: 0xFF6200EE),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Question
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(course?.color ?: 0xFF6200EE).copy(alpha = 0.1f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Câu hỏi ${currentQuizIndex + 1}",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(course?.color ?: 0xFF6200EE)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = quiz.question,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Answer options
                    quiz.options.forEachIndexed { index, option ->
                        AnswerOption(
                            text = option,
                            index = index,
                            isSelected = selectedAnswer == index,
                            isCorrect = index == quiz.correctAnswerIndex,
                            showResult = showExplanation,
                            courseColor = course?.color ?: 0xFF6200EE,
                            onClick = {
                                if (!showExplanation) {
                                    selectedAnswer = index
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Explanation
                    if (showExplanation) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedAnswer == quiz.correctAnswerIndex)
                                    Color(0xFF4CAF50).copy(alpha = 0.1f)
                                else
                                    Color(0xFFF44336).copy(alpha = 0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = if (selectedAnswer == quiz.correctAnswerIndex) "✓ Chính xác!" else "✗ Chưa đúng",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedAnswer == quiz.correctAnswerIndex)
                                        Color(0xFF4CAF50)
                                    else
                                        Color(0xFFF44336)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = quiz.explanation,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action button
                    Button(
                        onClick = {
                            if (!showExplanation && selectedAnswer != null) {
                                showExplanation = true
                                if (selectedAnswer == quiz.correctAnswerIndex) {
                                    correctAnswers++
                                }
                            } else if (showExplanation) {
                                if (currentQuizIndex < quizzes.size - 1) {
                                    currentQuizIndex++
                                    selectedAnswer = null
                                    showExplanation = false
                                } else {
                                    quizCompleted = true
                                    viewModel.saveQuizScore(quiz.id, correctAnswers)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        enabled = selectedAnswer != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(course?.color ?: 0xFF6200EE)
                        )
                    ) {
                        Text(
                            if (!showExplanation) "Kiểm tra"
                            else if (currentQuizIndex < quizzes.size - 1) "Câu tiếp theo"
                            else "Xem kết quả"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun AnswerOption(
    text: String,
    index: Int,
    isSelected: Boolean,
    isCorrect: Boolean,
    showResult: Boolean,
    courseColor: Long,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            showResult && isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.2f)
            showResult && isSelected && !isCorrect -> Color(0xFFF44336).copy(alpha = 0.2f)
            isSelected -> Color(courseColor).copy(alpha = 0.2f)
            else -> Color.Transparent
        },
        label = "background"
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            showResult && isCorrect -> Color(0xFF4CAF50)
            showResult && isSelected && !isCorrect -> Color(0xFFF44336)
            isSelected -> Color(courseColor)
            else -> MaterialTheme.colorScheme.outline
        },
        label = "border"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ('A' + index).toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = borderColor,
                modifier = Modifier.padding(end = 16.dp)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            if (showResult && isCorrect) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Đúng",
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}


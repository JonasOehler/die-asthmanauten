package com.example.asthmaapp.ui.screens.protocol.act

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.asthmaapp.R
import com.example.asthmaapp.model.ACTEntry
import com.example.asthmaapp.model.ACTResultType
import com.example.asthmaapp.ui.components.ProgressDots
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ACTQuestionnaireScreen(
    onQuestionnaireFinished: (ACTEntry) -> Unit,
    onCancel: () -> Unit
) {
    val questionnaireStep = remember { mutableIntStateOf(0) }
    val answers = remember { mutableStateListOf("", "", "", "", "") }
    val context = LocalContext.current
    val questions = listOf(
        stringResource(id = R.string.act_question_1),
        stringResource(id = R.string.act_question_2),
        stringResource(id = R.string.act_question_3),
        stringResource(id = R.string.act_question_4),
        stringResource(id = R.string.act_question_5)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val currentDate = LocalDate.now()
                    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
                    val formattedDate = currentDate.format(dateFormatter)

                    Text("$formattedDate")
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.previous)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->

            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp), // This padding is for spacing from the screen edges
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            val baseTitle = stringResource(id = R.string.act_questionnaire_title)
                            Text(
                                text = "$baseTitle ${questionnaireStep.intValue + 1}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Text(
                                text = questions[questionnaireStep.intValue],
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                (1..5).forEach { option ->
                                    val isSelected =
                                        answers[questionnaireStep.intValue] == option.toString()
                                    val answerStringId =
                                        remember(questionnaireStep.intValue, option) {
                                            val resourceName =
                                                "act_q${questionnaireStep.intValue + 1}_option_${option}"
                                            context.resources.getIdentifier(
                                                resourceName,
                                                "string",
                                                context.packageName
                                            )
                                        }

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                answers[questionnaireStep.intValue] =
                                                    option.toString()
                                            },
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh
                                        ),
                                        border = if (isSelected) BorderStroke(
                                            2.dp,
                                            MaterialTheme.colorScheme.primary
                                        ) else null,
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = if (answerStringId != 0) stringResource(id = answerStringId) else "",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                    HorizontalDivider()
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (questionnaireStep.intValue > 0) {
                                    TextButton(onClick = { questionnaireStep.intValue-- }) {
                                        Text(stringResource(id = R.string.previous))
                                    }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }

                                if (questionnaireStep.intValue < questions.size - 1) {
                                    TextButton(
                                        onClick = { questionnaireStep.intValue++ },
                                        enabled = answers[questionnaireStep.intValue].isNotEmpty()
                                    ) {
                                        Text(stringResource(id = R.string.next))
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            val score = answers.sumOf { it.toIntOrNull() ?: 0 }
                                            val resultType = when {
                                                score >= 20 -> ACTResultType.GOOD
                                                score >= 16 -> ACTResultType.NEUTRAL
                                                else -> ACTResultType.BAD
                                            }

                                            val newEntry = ACTEntry(
                                                id = UUID.randomUUID().toString(),
                                                date = LocalDate.now()
                                                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                                score = score,
                                                answers = answers.toList(),
                                                resultType = resultType
                                            )
                                            onQuestionnaireFinished(newEntry)
                                        },
                                        enabled = answers[questionnaireStep.intValue].isNotEmpty()
                                    ) {
                                        Text(stringResource(id = R.string.save_result))
                                    }
                                }
                            }
                        }
                    }

                    ProgressDots(
                        totalSteps = questions.size,
                        currentStep = questionnaireStep.intValue,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

            }
        }
    )
}
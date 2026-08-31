package com.example.asthmaapp.ui.screens.protocol.act

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.example.asthmaapp.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.asthmaapp.model.ACTEntry
import com.example.asthmaapp.model.ACTResultType

@Composable
fun ACTEntryCard(
    entry: ACTEntry
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = entry.date,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.act_result),
            contentDescription = "ACT Result Chart Screenshot",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (entry.resultType) {
                ACTResultType.BAD -> {
                    Icon(
                        painter = painterResource(id = R.drawable.sentiment_dissatisfied),
                        contentDescription = "Frown",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = stringResource(id = R.string.act_title_bad_result),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = stringResource(id = R.string.act_body_bad_result),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }

                ACTResultType.GOOD -> {
                    Icon(
                        painter = painterResource(id = R.drawable.sentiment_satisfied_24px),
                        contentDescription = "Smile",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = stringResource(id = R.string.act_title_good_result),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = stringResource(id = R.string.act_body_good_result),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }

                ACTResultType.NEUTRAL -> {
                    Icon(
                        painter = painterResource(id = R.drawable.sentiment_neutral_24px),
                        contentDescription = "Neutral Face",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = stringResource(id = R.string.act_title_neutral_result),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = stringResource(id = R.string.act_body_neutral_result),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                val questions = listOf(
                    stringResource(id = R.string.act_question_1),
                    stringResource(id = R.string.act_question_2),
                    stringResource(id = R.string.act_question_3),
                    stringResource(id = R.string.act_question_4),
                    stringResource(id = R.string.act_question_5)
                )
                val baseQuestionnaireTitle = stringResource(id = R.string.act_questionnaire_title)
                entry.answers.forEachIndexed { index, userAnswerNumber ->
                    val questionText = questions[index]

                    Text(
                        text = "$baseQuestionnaireTitle ${index + 1}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    val answerStringId = remember(index, userAnswerNumber) {
                        val resourceName = "act_q${index + 1}_option_${userAnswerNumber}"
                        context.resources.getIdentifier(resourceName, "string", context.packageName)
                    }

                    val answerText = if (answerStringId != 0) {
                        stringResource(id = answerStringId)
                    } else {
                        "Answer text not found for option $userAnswerNumber"
                    }

                    Text(
                        text = "${index + 1}. $questionText",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = "$answerText",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
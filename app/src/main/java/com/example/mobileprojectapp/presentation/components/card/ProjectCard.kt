package com.example.mobileprojectapp.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Paid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileprojectapp.domain.model.ProjectItem

@Composable
fun ProjectCard(project: ProjectItem){

    val daysStatusColor = when {
        project.daysRemaining < 0 -> Color.Red          // overdue
        project.daysRemaining.toInt() == 0 -> Color(0xFFFFA000) // today
        project.daysRemaining.toInt() == 1 -> Color(0xFFFFC107) // tomorrow / 1 day left
        project.daysRemaining <= 7 -> Color(0xFFFFD54F) // due this week
        project.daysRemaining <= 30 -> Color(0xFF4CAF50) // due this month (green-ish)
        else -> Color(0xFF81C784) // far away
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Gray)

    ) {
        Row {
            Box(
                modifier = Modifier
                    .weight(0.70f)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {

                    Column {
                        Row {
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 5.dp, end = 5.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(daysStatusColor)
                            ){
                                Text(
                                    text = project.daysRemainingStatus,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),

                                    )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.LightGray)
                            ){
                                Text(
                                    text = project.categoryName,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),

                                    )
                            }
                        }
                        Text(
                            text = project.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircleOutline,
                            contentDescription = "Todolist check",
                            modifier = Modifier
                                .padding(end = 3.dp)
                                .size(18.dp)

                        )
                        Text(
                            text = "Task ${ project.totalTodolistItemDone }/${ project.totalTodolistItem }",
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(end = 5.dp)
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(Color(0xffFFE0A6))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 5.dp, end = 10.dp, top = 1.dp, bottom = 1.dp)
                            ) {
                                Image(
                                    imageVector = Icons.Rounded.Paid,
                                    contentDescription = "budget",
                                    modifier = Modifier
                                        .size(18.dp)
                                )
                                Text(
                                    text = project.budget,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.30f)
            ) {
                CircularProgressIndicator(
                    progress = { 0.74f },
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.secondary,
                )

                Text("100%")
            }
        }

    }
}
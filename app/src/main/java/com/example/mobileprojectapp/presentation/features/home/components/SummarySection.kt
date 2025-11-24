package com.example.mobileprojectapp.presentation.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Paid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummarySection( totalProjects: String, totalProjectsDone: String, budgetUsed: String ){
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(17.dp))
            .background(MaterialTheme.colorScheme.secondary),
    ) {
        // Row 1
        Row(
            modifier = Modifier.fillMaxWidth().padding( start = 20.dp, end = 20.dp, top = 20.dp ),
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Total Projects"
                )
                Text(
                    text = totalProjects,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Projects done"
                )
                Text(
                    text = totalProjectsDone,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
            }
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Budget used"
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
                        contentDescription = "Auth Icon",
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Text(
                        text = budgetUsed
                    )
                }
            }
        }
        
    }
}
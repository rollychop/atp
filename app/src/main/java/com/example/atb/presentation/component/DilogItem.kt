package com.example.atb.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun DialogItem(
    title: String,
    bodyText: String,
    isError: Boolean = false,
    onCloseClick: (() -> Unit)
) {
    Popup(
        onDismissRequest = onCloseClick,
        alignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = if (isError) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = if (isError) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = bodyText,
                    textAlign = TextAlign.Justify
                )

            }

        }
    }
}
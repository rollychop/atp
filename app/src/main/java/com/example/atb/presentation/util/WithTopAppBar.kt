package com.example.atb.presentation.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.atb.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithTopAppBar(
    modifier: Modifier = Modifier,
    leftIcon: ImageVector? = Icons.Rounded.ArrowBack,
    contentDescription: String? = null,
    title: String = stringResource(id = R.string.app_name),
    onLeftIconClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Scaffold(topBar = {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftIcon?.let {
                IconButton(onClick = { onLeftIconClick?.invoke() }) {
                    Icon(
                        imageVector = leftIcon,
                        contentDescription = contentDescription
                    )
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }) {
        Box(modifier = modifier.padding(it)) {
            content()
        }
    }
}
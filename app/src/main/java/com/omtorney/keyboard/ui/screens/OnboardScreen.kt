package com.omtorney.keyboard.ui.screens

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omtorney.keyboard.ui.theme.KeyboardTheme

@Composable
fun OnboardScreen(
    onTestClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column {
            StepRow(
                stepTitle = "1.",
                buttonText = "Активировать клавиатуру",
                onClick = { context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            StepRow(
                stepTitle = "2.",
                buttonText = "Выбрать клавиатуру",
                onClick = { inputMethodManager.showInputMethodPicker() }
            )
            Spacer(modifier = Modifier.height(12.dp))
            StepRow(
                stepTitle = "3.",
                buttonText = "Настройки клавиатуры",
                onClick = onSettingsClick
            )
            Spacer(modifier = Modifier.height(12.dp))
            StepRow(
                stepTitle = "4.",
                buttonText = "Протестировать клавиатуру",
                onClick = onTestClick
            )
        }
    }
}

@Composable
fun StepRow(
    stepTitle: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stepTitle,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.width(12.dp))
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = buttonText.uppercase(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
fun OnBoardScreenPreview() {
    KeyboardTheme {
        Surface {
            OnboardScreen({}, {})
        }
    }
}

package com.omtorney.keyboard.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omtorney.keyboard.settings.SettingsStorage
import com.omtorney.keyboard.ui.screens.KeyboardScreen

class MyKeyboardView(
    context: Context,
    private val settingsStorage: SettingsStorage,
    private val onKeyClick: (String) -> Unit,
    private val onShiftClick: () -> Unit,
    private val onDeleteClick: () -> Unit
) : AbstractComposeView(context) {

    @Composable
    override fun Content() {
        val color = settingsStorage.keyboardColor.collectAsStateWithLifecycle(0L)
        KeyboardScreen(
            color = Color(color.value),
            onKeyClick = onKeyClick,
            onShiftClick = onShiftClick,
            onDeleteClick = onDeleteClick
        )
    }
}

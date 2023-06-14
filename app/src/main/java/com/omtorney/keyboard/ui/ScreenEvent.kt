package com.omtorney.keyboard.ui

import androidx.compose.ui.graphics.Color

sealed class ScreenEvent {
    data class SetColor(val color: Color) : ScreenEvent()
}

package com.omtorney.keyboard.ui

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omtorney.keyboard.settings.SettingsStorage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingsStorage: SettingsStorage
) : ViewModel() {

    val color: StateFlow<Long> = settingsStorage.keyboardColor.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = 0xFF32C0D5
    )

    fun onEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.SetColor -> {
                viewModelScope.launch {
                    settingsStorage.setKeyboardColor(event.color.toArgb().toLong())
                }
            }
        }
    }
}

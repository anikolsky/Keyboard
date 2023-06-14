package com.omtorney.keyboard.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_keyboard_settings")

class SettingsStorage(private val context: Context) {

    private companion object {
        val KEYBOARD_COLOR = longPreferencesKey("color")
    }

    val keyboardColor: Flow<Long> = context.dataStore.data
        .map { it[KEYBOARD_COLOR] ?: 0xFF32C0D5 }

    suspend fun setKeyboardColor(color: Long) {
        context.dataStore.edit { it[KEYBOARD_COLOR] = color }
    }
}

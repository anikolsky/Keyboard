package com.omtorney.keyboard.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.omtorney.keyboard.ui.ScreenEvent

@Composable
fun SettingsScreen(
    color: Color,
    onEvent: (ScreenEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        Color(0xFF1EB980),
        Color(0xFF045D56),
        Color(0xFFFF6859),
        Color(0xFFFFCF44),
        Color(0xFFB15DFF),
        Color(0xFF72DEFF),
        Color(0xFF173d96)
    )

    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            text = "Настройки",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Цвета"
            )
            Text(text = "Выберите цвет клавиатуры")
            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { expanded = true }
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(10))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(10)
                            )
                            .background(color),
                        onDraw = {}
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop down"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { element ->
                        DropdownMenuItem(
                            text = {
                                Canvas(
                                    modifier = Modifier
                                        .size(width = 90.dp, height = 30.dp)
                                        .clip(RoundedCornerShape(10))
                                        .border(
                                            width = 1.dp,
                                            color = Color.Gray,
                                            shape = RoundedCornerShape(10)
                                        )
                                        .background(element),
                                    onDraw = {}
                                )
                            },
                            onClick = {
                                onEvent(ScreenEvent.SetColor(Color(element.value)))
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

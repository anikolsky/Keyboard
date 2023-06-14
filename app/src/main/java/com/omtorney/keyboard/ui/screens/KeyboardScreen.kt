package com.omtorney.keyboard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omtorney.keyboard.ui.theme.KeyboardTheme

@Composable
fun KeyboardScreen(
    color: Color,
    onKeyClick: (String) -> Unit,
    onShiftClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val keys = listOf(
        "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
        "a", "s", "d", "f", "g", "h", "j", "k", "l",
        "z", "x", "c", "v", "b", "n", "m"
    )
    var caps by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.onBackground)
    ) {
        keys.chunked(10).forEach { rowKeys ->
            Row(horizontalArrangement = Arrangement.Center) {
                rowKeys.forEach { key ->
                    KeyButton(
                        modifier = Modifier
                            .padding(horizontal = 2.dp, vertical = 0.dp)
                            .width(30.dp),
                        color = color,
                        onClick = { onKeyClick(key) }
                    ) {
                        Text(
                            text = if (caps) key.uppercase() else key,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            KeyButton(
                onClick = {
                    onShiftClick()
                    caps = !caps
                },
                color = color,
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Shift"
                )
            }
            KeyButton(
                onClick = { onKeyClick(" ") },
                color = color,
            ) {
                Text(
                    text = "________",
                    modifier = Modifier.padding(horizontal = 40.dp)
                )
            }
            KeyButton(
                onClick = { onDeleteClick() },
                color = color,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Backspace"
                )
            }
        }
    }
}

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    color: Color,
    shape: CornerBasedShape = MaterialTheme.shapes.extraSmall,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        content()
    }
}

@Preview
@Composable
fun KeyboardPreview() {
    KeyboardTheme {
        Surface {
            KeyboardScreen(Color.DarkGray, {}, {}, {})
        }
    }
}

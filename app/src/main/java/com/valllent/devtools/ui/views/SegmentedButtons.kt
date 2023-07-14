package com.valllent.devtools.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedButtons(
    buttons: List<String>,
    selectedIndex: Int,
    enabled: Boolean,
    onButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier.fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp),
            )
    ) {
        buttons.forEachIndexed { index, buttonText ->
            Button(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                shape = RectangleShape,
                colors = if (index == selectedIndex) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                enabled = enabled,
                onClick = {
                    onButtonClick(index)
                },
            ) {
                Text(
                    text = buttonText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}
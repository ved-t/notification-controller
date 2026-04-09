package com.example.notificationcontroller.presentation.screens.vibration


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notificationcontroller.domain.model.VibrationLevel

@Composable
fun VibrationScreen(
    state: VibrationState,
    onLevelSelected: (VibrationLevel) -> Unit,
    onVibrateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Vibration Test Bench",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Level Selection
        VibrationLevel.entries.forEach { level ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = (level == state.selectedLevel),
                    onClick = { onLevelSelected(level) }
                )
                Text(
                    text = "${level.name} (${level.duration}ms)",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onVibrateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("VIBRATE")
        }
    }
}
package com.example.vinilos.ui.components.albums

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vinilos.ui.theme.CoveCream
import com.example.vinilos.ui.theme.CoveDarkBlue
import com.example.vinilos.ui.theme.CoveLightBlue
import com.example.vinilos.ui.theme.CoveOrange
import com.example.vinilos.ui.theme.CoveTextDark

@Composable
fun AddTrackDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddTrack: (name: String, duration: String) -> Unit
) {
    if (showDialog) {
        var trackName by remember { mutableStateOf("") }
        var trackDuration by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf<String?>(null) }
        var durationError by remember { mutableStateOf<String?>(null) }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = CoveDarkBlue,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ) {
                        Text(
                            text = "Agregar Track",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CoveCream.copy(alpha = 0.3f))
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = trackName,
                            onValueChange = {
                                trackName = it
                                nameError = if (it.isBlank()) "El nombre no puede estar vacío" else null
                            },
                            label = { Text("Nombre del track") },
                            isError = nameError != null,
                            supportingText = { nameError?.let { Text(it, color = Color.Red) } },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = CoveLightBlue,
                                focusedLabelColor = CoveDarkBlue
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = trackDuration,
                            onValueChange = {
                                trackDuration = it
                                durationError = validateDuration(it)
                            },
                            label = { Text("Duración (mm:ss)") },
                            isError = durationError != null,
                            supportingText = { durationError?.let { Text(it, color = Color.Red) } },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = CoveLightBlue,
                                focusedLabelColor = CoveDarkBlue
                            )
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = CoveDarkBlue
                            )
                        ) {
                            Text(
                                "Cancelar",
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                nameError = if (trackName.isBlank()) "El nombre no puede estar vacío" else null
                                durationError = validateDuration(trackDuration)

                                if (nameError == null && durationError == null) {
                                    onAddTrack(trackName, trackDuration)
                                    onDismiss()
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CoveOrange,
                                contentColor = CoveTextDark
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                "Agregar",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun validateDuration(duration: String): String? {
    if (duration.isBlank()) {
        return "La duración no puede estar vacía"
    }

    val regex = Regex("^\\d{1,2}:\\d{2}$")
    if (!regex.matches(duration)) {
        return "Formato inválido. Use mm:ss (ej: 3:45)"
    }

    return null
}

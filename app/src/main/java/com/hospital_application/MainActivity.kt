package com.hospital_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NurseSearchScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NurseSearchScreenPreview() {
    NurseSearchScreen()
}

@Composable
fun NurseSearchScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var showResults by remember { mutableStateOf(false) }

    // Nurse Class
    data class Nurse(
        val name: String,
        val user: String,
        val password: String
    )

    // Nurse List
    val nurses = listOf(
        Nurse("Sergio", "sergio.nurse", "sergio123"),
        Nurse("Nil", "nil.nurse", "nil123"),
        Nurse("Joan Albert", "joanalbert.nurse", "joanalbert123"),
        Nurse("Gerard", "gerard.nurse", "gerard123"),
        Nurse("Sergio", "sergio.nurse", "sergio123"),
        Nurse("Joan Albert", "joanalbert.nurse", "joanalbert123"),
        Nurse("Gerard", "gerard.nurse", "gerard123"),
        Nurse("Sergio", "sergio.nurse", "sergio123"),
        Nurse("Joan Albert", "joanalbert.nurse", "joanalbert123"),
        Nurse("Gerard", "gerard.nurse", "gerard123"),
        Nurse("Sergio", "sergio.nurse", "sergio123"),
        Nurse("Nil", "nil.nurse", "nil123"),
        Nurse("Gerard", "gerard.nurse", "gerard123")
    )

    // Check if search field is empty
    val filteredNurses = if (searchQuery.text.isEmpty()) {
        emptyList()
    } else {
        nurses.filter { it.name.contains(searchQuery.text, ignoreCase = true) }
    }

    // Limit to 3 results
    val limitedNurses = filteredNurses.take(3)

    // Create screen structure
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Search Nurses", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Search field
        BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (searchQuery.text.isEmpty()) {
                            Text(text = "Insert a name...", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                        }
                        innerTextField()
                    }
                }
            }
        )

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Search button
        Button(
            onClick = { showResults = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }

        // Spacer
        Spacer(modifier = Modifier.height(16.dp))

        // Show results if search button was clicked
        if (showResults) {
            // If nurses were found
            if (limitedNurses.isNotEmpty()) {
                Text(text = "${limitedNurses.size} nurses found:", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(8.dp))

                // Show results
                for (nurse in limitedNurses) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        shadowElevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Name: ${nurse.name}", style = MaterialTheme.typography.titleMedium)
                            Text(text = "User: ${nurse.user}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Password: ${nurse.password}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    // Spacer
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                // If no nurses were found
                Text(
                    text = "No nurse called '${searchQuery.text}' was found",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
package com.example.expenseiq_clean

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.expenseiq_clean.presentation.view.add_expense.AddExpenseScreen
import com.example.expenseiq_clean.presentation.view.dashboard.DashboardScreen
import com.example.expenseiq_clean.ui.theme.ExpenseIQ_CleanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        askRequiredPermissions()
        setContent {
            ExpenseIQ_CleanTheme {
                AddExpenseScreen()
            }
        }
    }

    private fun askRequiredPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat
                .requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    101
                )
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseIQ_CleanTheme {
        Greeting("Android")
    }
}
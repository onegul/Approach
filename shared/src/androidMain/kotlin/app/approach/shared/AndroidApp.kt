package app.approach.shared

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

fun ComponentActivity.setApproachContent() {
    setContent {
        App()
    }
}
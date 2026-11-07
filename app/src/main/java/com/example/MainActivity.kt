package com.example

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.ui.screens.TvHomeScreen
import com.example.ui.theme.FireDarkBg
import com.example.ui.theme.FireOrange
import com.example.ui.theme.FireTextPrimary
import com.example.ui.theme.FireTextSecondary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Strict Landscape Orientation (16:9) required for Android TV and FireOS
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        // Immersive TV mode (hide system bars)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.hide(WindowInsetsCompat.Type.systemBars())
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(FireDarkBg)
                ) { innerPadding ->
                    TvHomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        // Native D-Pad key routing for Android TV & TCL Smart TVs
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_DPAD_UP,
                KeyEvent.KEYCODE_DPAD_DOWN,
                KeyEvent.KEYCODE_DPAD_LEFT,
                KeyEvent.KEYCODE_DPAD_RIGHT,
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_ENTER,
                KeyEvent.KEYCODE_BACK,
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                    // Let Android TV spatial focus manager naturally navigate
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }
}

/**
 * Virtual TV Remote Component for testing in Browser Emulator
 */
@Composable
fun VirtualFireTvRemote(
    onAction: (String) -> Unit,
    onClose: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color(0xF0141B26),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0x33FF9900)),
        modifier = Modifier
            .width(200.dp)
            .shadow(16.dp, RoundedCornerShape(24.dp))
            .testTag("virtual_remote_panel")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FIRE TV REMOTE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = FireOrange,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "✕",
                    fontSize = 13.sp,
                    color = FireTextSecondary,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onClose() }
                        .padding(4.dp)
                )
            }

            // D-PAD CIRCLE
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(Color(0xFF1E2837), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { onAction("UP") },
                    modifier = Modifier.align(Alignment.TopCenter).size(38.dp)
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Arriba", tint = FireTextPrimary)
                }

                IconButton(
                    onClick = { onAction("DOWN") },
                    modifier = Modifier.align(Alignment.BottomCenter).size(38.dp)
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Abajo", tint = FireTextPrimary)
                }

                IconButton(
                    onClick = { onAction("LEFT") },
                    modifier = Modifier.align(Alignment.CenterStart).size(38.dp)
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Izquierda", tint = FireTextPrimary)
                }

                IconButton(
                    onClick = { onAction("RIGHT") },
                    modifier = Modifier.align(Alignment.CenterEnd).size(38.dp)
                ) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Derecha", tint = FireTextPrimary)
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(FireOrange)
                        .clickable { onAction("SELECT") },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "OK",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        color = Color(0xFF0C1017)
                    )
                }
            }

            // Functions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RemoteSmallButton(icon = Icons.Default.ArrowBack, contentDesc = "Atrás", onClick = { onAction("BACK") })
                RemoteSmallButton(icon = Icons.Default.Home, contentDesc = "Inicio", onClick = { onAction("HOME") })
                RemoteSmallButton(icon = Icons.Default.PlayArrow, contentDesc = "Play", onClick = { onAction("PLAY_PAUSE") })
                RemoteSmallButton(icon = Icons.Default.Menu, contentDesc = "Menú", onClick = { onAction("BACK") })
            }
        }
    }
}

@Composable
fun RemoteSmallButton(
    icon: ImageVector,
    contentDesc: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(Color(0xFF243042))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDesc,
            tint = FireTextPrimary,
            modifier = Modifier.size(16.dp)
        )
    }
}

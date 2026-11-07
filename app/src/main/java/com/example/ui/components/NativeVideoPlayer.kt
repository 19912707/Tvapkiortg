package com.example.ui.components

import android.net.Uri
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.model.MediaItem
import com.example.ui.theme.FireBlue
import com.example.ui.theme.FireOrange
import com.example.ui.theme.FireTextPrimary
import com.example.ui.theme.FireTextSecondary
import kotlinx.coroutines.delay

@Composable
fun NativeVideoPlayer(
    item: MediaItem,
    onClose: () -> Unit
) {
    var videoViewRef by remember { mutableStateOf<VideoView?>(null) }
    var isPlaying by remember { mutableStateOf(true) }
    var showHud by remember { mutableStateOf(true) }
    var currentPositionMs by remember { mutableIntStateOf(0) }
    var durationMs by remember { mutableIntStateOf(1) }
    var progressFraction by remember { mutableFloatStateOf(0f) }

    // Intercept hardware Back button to close player
    BackHandler {
        onClose()
    }

    // Auto-hide HUD after 4 seconds
    LaunchedEffect(showHud, isPlaying) {
        if (showHud && isPlaying) {
            delay(4000)
            showHud = false
        }
    }

    // Time update loop
    LaunchedEffect(isPlaying) {
        while (true) {
            videoViewRef?.let { vv ->
                if (vv.isPlaying) {
                    val pos = vv.currentPosition
                    val dur = vv.duration.coerceAtLeast(1)
                    currentPositionMs = pos
                    durationMs = dur
                    progressFraction = (pos.toFloat() / dur.toFloat()).coerceIn(0f, 1f)
                }
            }
            delay(500)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("native_video_player")
    ) {
        // Native Hardware VideoView
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                VideoView(context).apply {
                    setVideoURI(Uri.parse(item.videoUrl))
                    setOnPreparedListener { mp ->
                        mp.isLooping = true
                        start()
                        isPlaying = true
                        durationMs = mp.duration
                    }
                    setOnCompletionListener {
                        onClose()
                    }
                    videoViewRef = this
                }
            }
        )

        // Overlay Scrim & TV Player HUD
        AnimatedVisibility(
            visible = showHud,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.85f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.90f)
                            )
                        )
                    )
                    .padding(32.dp)
            ) {
                // TOP BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        IconButton(
                            onClick = onClose,
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color(0x44FFFFFF), CircleShape)
                                .testTag("video_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.White
                            )
                        }

                        Column {
                            Text(
                                text = item.title,
                                color = FireTextPrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.subtitle.ifEmpty { "Transmisión 4K Ultra HD • Sonido 5.1" },
                                color = FireBlue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = FireOrange,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "FIRE TV PLAYER",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0C1017),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                // BOTTOM CONTROLS
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Timeline Scrubber
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = formatMs(currentPositionMs),
                            color = FireTextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .weight(1f)
                                .size(height = 6.dp, width = 0.dp),
                            color = FireOrange,
                            trackColor = Color(0x44FFFFFF)
                        )

                        Text(
                            text = formatMs(durationMs),
                            color = FireTextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Playback Buttons Row (Rewind 10s, Play/Pause, Forward 10s)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                videoViewRef?.let {
                                    val newPos = (it.currentPosition - 10000).coerceAtLeast(0)
                                    it.seekTo(newPos)
                                }
                                showHud = true
                            },
                            modifier = Modifier
                                .size(46.dp)
                                .background(Color(0x33FFFFFF), CircleShape)
                                .testTag("video_rewind_10")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Replay10,
                                contentDescription = "Retroceder 10s",
                                tint = Color.White
                            )
                        }

                        IconButton(
                            onClick = {
                                videoViewRef?.let { vv ->
                                    if (vv.isPlaying) {
                                        vv.pause()
                                        isPlaying = false
                                    } else {
                                        vv.start()
                                        isPlaying = true
                                    }
                                }
                                showHud = true
                            },
                            modifier = Modifier
                                .size(60.dp)
                                .background(FireOrange, CircleShape)
                                .testTag("video_toggle_play_pause")
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                                tint = Color(0xFF0C1017),
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                videoViewRef?.let {
                                    val newPos = (it.currentPosition + 10000).coerceAtMost(it.duration)
                                    it.seekTo(newPos)
                                }
                                showHud = true
                            },
                            modifier = Modifier
                                .size(46.dp)
                                .background(Color(0x33FFFFFF), CircleShape)
                                .testTag("video_forward_10")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Forward10,
                                contentDescription = "Adelantar 10s",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            videoViewRef?.stopPlayback()
        }
    }
}

private fun formatMs(ms: Int): String {
    val totalSeconds = (ms / 1000).coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

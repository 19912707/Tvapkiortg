package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.model.MediaItem
import com.example.ui.theme.FireBlue
import com.example.ui.theme.FireDarkSurface
import com.example.ui.theme.FireOrange
import com.example.ui.theme.FireTextPrimary
import com.example.ui.theme.FireTextSecondary

@Composable
fun MediaCard(
    item: MediaItem,
    onFocused: (MediaItem) -> Unit,
    onClick: (MediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    // Native Focus Animation: 1.08x scale and glowing orange border
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.08f else 1.0f,
        animationSpec = tween(durationMillis = 200),
        label = "cardScale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) FireOrange else Color.Transparent,
        animationSpec = tween(durationMillis = 180),
        label = "cardBorderColor"
    )

    val cardWidth = if (item.isPoster) 140.dp else 220.dp
    val cardHeight = if (item.isPoster) 210.dp else 124.dp

    Column(
        modifier = modifier
            .zIndex(if (isFocused) 10f else 1f)
            .width(cardWidth)
            .padding(vertical = 10.dp, horizontal = 6.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = FireDarkSurface),
            border = BorderStroke(2.dp, borderColor),
            modifier = Modifier
                .width(cardWidth)
                .height(cardHeight)
                .scale(scale)
                .shadow(
                    elevation = if (isFocused) 16.dp else 2.dp,
                    shape = RoundedCornerShape(8.dp),
                    ambientColor = if (isFocused) FireOrange else Color.Black,
                    spotColor = if (isFocused) FireOrange else Color.Black
                )
                .focusable()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (focusState.isFocused) {
                        onFocused(item)
                    }
                }
                .clickable { onClick(item) }
                .testTag("media_card_${item.id}")
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Media Thumbnail via Coil
                AsyncImage(
                    model = item.posterUrl,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Bottom vignette gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xE60C1017)),
                                startY = 80f
                            )
                        )
                )

                // Live TV indicator badge
                if (item.isLive) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(Color(0xFFDC2626), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Color.White, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "EN VIVO",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                // Title and subtitle overlay at the bottom
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = item.title,
                        color = FireTextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (item.subtitle.isNotEmpty()) {
                        Text(
                            text = item.subtitle,
                            color = if (isFocused) FireOrange else FireBlue,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Continue Watching Progress Bar
                if (item.progress != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .align(Alignment.BottomStart)
                            .background(Color(0x55FFFFFF))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction = item.progress / 100f)
                                .height(4.dp)
                                .background(if (isFocused) Color.White else FireOrange)
                        )
                    }
                }
            }
        }
    }
}

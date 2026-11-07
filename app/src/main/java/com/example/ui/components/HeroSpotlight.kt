package com.example.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.MediaItem
import com.example.ui.theme.FireBlue
import com.example.ui.theme.FireDarkBg
import com.example.ui.theme.FireOrange
import com.example.ui.theme.FireTextPrimary
import com.example.ui.theme.FireTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeroSpotlight(
    item: MediaItem,
    onPlayClick: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(290.dp)
            .background(FireDarkBg)
    ) {
        // Dynamic Backdrop with Crossfade
        Crossfade(targetState = item.backdropUrl, label = "HeroBackdropCrossfade") { url ->
            AsyncImage(
                model = url,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Cinematic Vignette Gradients for Legibility on TV screens
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            FireDarkBg,
                            FireDarkBg.copy(alpha = 0.96f),
                            FireDarkBg.copy(alpha = 0.70f),
                            FireDarkBg.copy(alpha = 0.20f),
                            Color.Transparent
                        ),
                        startX = 0f,
                        endX = 1400f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            FireDarkBg.copy(alpha = 0.85f),
                            FireDarkBg
                        ),
                        startY = 100f
                    )
                )
        )

        // Hero Info Overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 48.dp, bottom = 20.dp)
                .fillMaxWidth(0.65f)
        ) {
            // Badges
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item.badges.forEach { badge ->
                    val isPrime = badge.equals("Prime", ignoreCase = true)
                    val isScore = badge.contains("8.") || badge.contains("9.") || badge.contains("IMDb")
                    val isLive = badge.equals("EN VIVO", ignoreCase = true)

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = when {
                            isLive -> Color(0xFFDC2626)
                            isPrime -> FireBlue
                            isScore -> FireOrange.copy(alpha = 0.2f)
                            else -> Color(0x33FFFFFF)
                        },
                        border = when {
                            isScore -> BorderStroke(1.dp, FireOrange)
                            else -> BorderStroke(1.dp, Color(0x22FFFFFF))
                        }
                    ) {
                        Text(
                            text = badge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isScore) FireOrange else Color.White,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = item.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = FireTextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Metadata row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = item.year, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FireTextSecondary)
                Text(text = "•", color = FireTextSecondary, fontSize = 10.sp)
                Text(text = item.rating, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FireTextSecondary)
                Text(text = "•", color = FireTextSecondary, fontSize = 10.sp)
                Text(text = item.duration, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FireTextSecondary)
                if (item.score.isNotEmpty()) {
                    Text(text = "•", color = FireTextSecondary, fontSize = 10.sp)
                    Text(text = "★ ${item.score}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FireOrange)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Synopsis
            Text(
                text = item.description,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = Color(0xFFCBD5E1),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TvHeroButton(
                    text = "Reproducir",
                    icon = Icons.Default.PlayArrow,
                    isPrimary = true,
                    onClick = onPlayClick,
                    testTag = "hero_play_button"
                )
                TvHeroButton(
                    text = "Más Información",
                    icon = Icons.Default.Info,
                    isPrimary = false,
                    onClick = onInfoClick,
                    testTag = "hero_info_button"
                )
            }
        }
    }
}

@Composable
fun TvHeroButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isPrimary: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isFocused) 1.08f else 1.0f, label = "buttonScale")

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = when {
            isFocused -> FireOrange
            isPrimary -> Color.White
            else -> Color(0x33FFFFFF)
        },
        border = BorderStroke(
            width = if (isFocused) 2.dp else 1.dp,
            color = if (isFocused) Color.White else Color.Transparent
        ),
        modifier = Modifier
            .scale(scale)
            .focusable()
            .onFocusChanged { isFocused = it.isFocused }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isFocused || isPrimary) FireDarkBg else Color.White,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (isFocused || isPrimary) FireDarkBg else Color.White
            )
        }
    }
}

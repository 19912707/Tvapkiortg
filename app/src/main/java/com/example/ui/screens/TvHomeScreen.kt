package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.VirtualFireTvRemote
import com.example.model.MediaItem
import com.example.model.SampleTvData
import com.example.ui.components.HeroSpotlight
import com.example.ui.components.MediaCard
import com.example.ui.components.NativeVideoPlayer
import com.example.ui.theme.FireDarkBg
import com.example.ui.theme.FireDarkSurface
import com.example.ui.theme.FireOrange
import com.example.ui.theme.FireTextPrimary
import com.example.ui.theme.FireTextSecondary

@Composable
fun TvHomeScreen(
    modifier: Modifier = Modifier
) {
    var activeHeroItem by remember { mutableStateOf(SampleTvData.defaultHero) }
    var activeVideoItem by remember { mutableStateOf<MediaItem?>(null) }
    var infoModalItem by remember { mutableStateOf<MediaItem?>(null) }
    var isRemoteVisible by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("home") }

    val columnListState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FireDarkBg)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // 1. COLLAPSIBLE TV SIDEBAR NAVIGATION
            TvSidebar(
                selectedCategory = selectedCategory,
                onCategorySelect = { category ->
                    selectedCategory = category
                }
            )

            // 2. MAIN TV FEED (HERO SPOTLIGHT + VIRTUALIZED ROWS)
            LazyColumn(
                state = columnListState,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .testTag("tv_content_feed"),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // HERO SPOTLIGHT (Upper 50-55% of the screen)
                item(key = "hero_spotlight_section") {
                    HeroSpotlight(
                        item = activeHeroItem,
                        onPlayClick = { activeVideoItem = activeHeroItem },
                        onInfoClick = { infoModalItem = activeHeroItem }
                    )
                }

                // VIRTUALIZED HORIZONTAL ROWS ('Continuar Viendo', 'En Vivo', 'Películas')
                items(SampleTvData.rows, key = { it.id }) { row ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 8.dp)
                    ) {
                        // Row Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 48.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = row.title,
                                color = FireTextPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "${row.items.size} Títulos",
                                color = FireTextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Virtualized Horizontal Row with FlatList behavior
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 44.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(row.items, key = { it.id }) { mediaItem ->
                                MediaCard(
                                    item = mediaItem,
                                    onFocused = { focusedItem ->
                                        activeHeroItem = focusedItem
                                    },
                                    onClick = { clickedItem ->
                                        activeVideoItem = clickedItem
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. FULLSCREEN NATIVE VIDEO PLAYER
        activeVideoItem?.let { videoItem ->
            NativeVideoPlayer(
                item = videoItem,
                onClose = { activeVideoItem = null }
            )
        }

        // 4. MORE INFO MODAL DIALOG
        infoModalItem?.let { infoItem ->
            AlertDialog(
                onDismissRequest = { infoModalItem = null },
                containerColor = FireDarkSurface,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = infoItem.title,
                            color = FireTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        IconButton(onClick = { infoModalItem = null }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar", tint = FireTextSecondary)
                        }
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "${infoItem.year} • ${infoItem.rating} • ${infoItem.duration} • ★ ${infoItem.score}",
                            color = FireOrange,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = infoItem.description,
                            color = Color(0xFFCBD5E1),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val target = infoItem
                            infoModalItem = null
                            activeVideoItem = target
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = FireOrange)
                    ) {
                        Text("Reproducir", color = Color(0xFF0C1017), fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { infoModalItem = null },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF))
                    ) {
                        Text("Cerrar", color = Color.White)
                    }
                }
            )
        }

        // 5. ON-SCREEN VIRTUAL REMOTE (for testing in browser emulator)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.End) {
                AnimatedVisibility(
                    visible = isRemoteVisible,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    VirtualFireTvRemote(
                        onAction = { /* handled natively via focus / clicks */ },
                        onClose = { isRemoteVisible = false }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    onClick = { isRemoteVisible = !isRemoteVisible },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isRemoteVisible) FireOrange else FireDarkSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, FireOrange),
                    modifier = Modifier.shadow(8.dp, RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tv,
                            contentDescription = "Mando",
                            tint = if (isRemoteVisible) Color(0xFF0C1017) else FireOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isRemoteVisible) "Ocultar Mando" else "Mando Fire TV",
                            color = if (isRemoteVisible) Color(0xFF0C1017) else FireTextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TvSidebar(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(if (isExpanded) 180.dp else 68.dp)
            .background(Color(0xFF0F172A))
            .border(width = 1.dp, color = Color(0x1AFFFFFF))
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo Fire TV
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(FireOrange),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "fire",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp
            )
        }

        // Nav Items List
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TvSidebarIcon(
                icon = Icons.Default.Search,
                label = "Buscar",
                isSelected = selectedCategory == "search",
                onClick = { onCategorySelect("search") }
            )
            TvSidebarIcon(
                icon = Icons.Default.Tv,
                label = "Inicio",
                isSelected = selectedCategory == "home",
                onClick = { onCategorySelect("home") }
            )
            TvSidebarIcon(
                icon = Icons.Default.LiveTv,
                label = "En Vivo",
                isSelected = selectedCategory == "live",
                onClick = { onCategorySelect("live") }
            )
            TvSidebarIcon(
                icon = Icons.Default.Movie,
                label = "Películas",
                isSelected = selectedCategory == "movies",
                onClick = { onCategorySelect("movies") }
            )
        }

        // Settings at bottom
        TvSidebarIcon(
            icon = Icons.Default.Settings,
            label = "Ajustes",
            isSelected = selectedCategory == "settings",
            onClick = { onCategorySelect("settings") }
        )
    }
}

@Composable
fun TvSidebarIcon(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(
                when {
                    isFocused -> FireOrange
                    isSelected -> Color(0x33FF9900)
                    else -> Color.Transparent
                }
            )
            .border(
                width = 1.5.dp,
                color = if (isFocused) Color.White else if (isSelected) FireOrange else Color.Transparent,
                shape = CircleShape
            )
            .focusable()
            .onFocusChanged { isFocused = it.isFocused }
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isFocused) Color(0xFF0C1017) else if (isSelected) FireOrange else FireTextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

package com.example.model

data class MediaItem(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val description: String = "",
    val year: String = "2024",
    val rating: String = "16+",
    val duration: String = "1h 45m",
    val score: String = "8.8",
    val badges: List<String> = listOf("Prime", "4K UHD", "HDR10+", "Dolby Atmos"),
    val backdropUrl: String,
    val posterUrl: String,
    val videoUrl: String,
    val progress: Int? = null,
    val isLive: Boolean = false,
    val channelNumber: String = "",
    val isPoster: Boolean = false,
    val category: String = "Películas"
)

data class MediaRow(
    val id: String,
    val title: String,
    val items: List<MediaItem>
)

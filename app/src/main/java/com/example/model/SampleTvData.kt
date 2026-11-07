package com.example.model

object SampleTvData {

    val defaultHero = MediaItem(
        id = "hero-rings",
        title = "El Señor de los Anillos: Los Anillos de Poder",
        subtitle = "La oscuridad comienza a extenderse por la Tierra Media",
        description = "En una época de aparente paz, una elfa guerrera llamada Galadriel descubre señales inquietantes de que un mal ancestral ha resurgido en las sombras de Mordor.",
        year = "2024",
        rating = "16+",
        duration = "2 Temporadas",
        score = "8.7",
        badges = listOf("Prime", "4K UHD", "HDR10+", "Dolby Atmos"),
        backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=1920&q=85",
        posterUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=600&q=80",
        videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    )

    val rows = listOf(
        MediaRow(
            id = "row_continue_watching",
            title = "Continuar Viendo",
            items = listOf(
                MediaItem(
                    id = "cw_fallout",
                    title = "Fallout",
                    subtitle = "T1 : Ep 5 - El Pasado",
                    description = "En un futuro postapocalíptico desolado por la guerra nuclear, los habitantes de los búnkeres deben regresar a una superficie llena de peligros.",
                    year = "2024",
                    rating = "18+",
                    duration = "55m",
                    score = "8.9",
                    badges = listOf("Prime", "4K HDR", "5.1"),
                    backdropUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                    progress = 68
                ),
                MediaItem(
                    id = "cw_the_boys",
                    title = "The Boys",
                    subtitle = "T4 : Ep 7 - Guerra Sucia",
                    description = "Un grupo de justicieros rebeldes se embarca en una misión heroica para desenmascarar a 'Los Siete' y la corrupta corporación Vought.",
                    year = "2024",
                    rating = "18+",
                    duration = "1h 02m",
                    score = "9.1",
                    badges = listOf("Prime", "4K UHD", "Dolby Vision"),
                    backdropUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                    progress = 42
                ),
                MediaItem(
                    id = "cw_reacher",
                    title = "Reacher",
                    subtitle = "T2 : Ep 2 - Hacia el Norte",
                    description = "Jack Reacher, un veterano investigador de la policía militar, se ve envuelto en una conspiración letal cuando sus compañeros son asesinados.",
                    year = "2023",
                    rating = "16+",
                    duration = "48m",
                    score = "8.6",
                    badges = listOf("Prime", "UHD", "Dolby Atmos"),
                    backdropUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                    progress = 85
                ),
                MediaItem(
                    id = "cw_invincible",
                    title = "Invincible",
                    subtitle = "T2 : Ep 8 - Confrontación",
                    description = "Mark Grayson es un chico normal, excepto por el pequeño detalle de que su padre es el superhéroe más poderoso del planeta.",
                    year = "2024",
                    rating = "18+",
                    duration = "50m",
                    score = "9.0",
                    badges = listOf("Prime", "HDR", "5.1"),
                    backdropUrl = "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    progress = 20
                )
            )
        ),

        MediaRow(
            id = "row_live_tv",
            title = "En Vivo (Canales FAST)",
            items = listOf(
                MediaItem(
                    id = "live_news_24",
                    title = "Noticias 24 Horas",
                    subtitle = "Edición Central del Mundo (18:00 - 19:30)",
                    description = "Información minuto a minuto, análisis geopolítico y reportajes especiales en tiempo real.",
                    year = "EN DIRECTO",
                    rating = "TP",
                    duration = "En directo",
                    score = "Noticias",
                    badges = listOf("EN VIVO", "HD", "CH 102"),
                    backdropUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    isLive = true,
                    channelNumber = "CH 102",
                    progress = 50
                ),
                MediaItem(
                    id = "live_sports_hd",
                    title = "Deportes Total HD",
                    subtitle = "Champions Matchday Previa",
                    description = "La mejor cobertura de fútbol internacional, resúmenes con gráficos tácticos y estadísticas en vivo.",
                    year = "EN DIRECTO",
                    rating = "TP",
                    duration = "En directo",
                    score = "Deportes",
                    badges = listOf("EN VIVO", "Full HD", "CH 204"),
                    backdropUrl = "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1508098682722-e99c43a406b2?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                    isLive = true,
                    channelNumber = "CH 204",
                    progress = 35
                ),
                MediaItem(
                    id = "live_cinema",
                    title = "Cine Acción Prime",
                    subtitle = "Misión Imposible: Protocolo Fantasma",
                    description = "Un maratón ininterrumpido con las secuencias de acción más trepidantes del cine contemporáneo.",
                    year = "EN DIRECTO",
                    rating = "16+",
                    duration = "133m",
                    score = "Cine",
                    badges = listOf("EN VIVO", "4K", "CH 310"),
                    backdropUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                    isLive = true,
                    channelNumber = "CH 310",
                    progress = 75
                ),
                MediaItem(
                    id = "live_wild",
                    title = "Planeta Salvaje 4K",
                    subtitle = "Depredadores de la Sabana Africana",
                    description = "Imágenes de naturaleza grabadas con cámaras de ultra alta velocidad y tomas aéreas cinematográficas.",
                    year = "EN DIRECTO",
                    rating = "TP",
                    duration = "60m",
                    score = "Naturaleza",
                    badges = listOf("EN VIVO", "4K HDR", "CH 450"),
                    backdropUrl = "https://images.unsplash.com/photo-1534188753412-3e26d0d618d6?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1534188753412-3e26d0d618d6?auto=format&fit=crop&w=800&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                    isLive = true,
                    channelNumber = "CH 450",
                    progress = 80
                )
            )
        ),

        MediaRow(
            id = "row_movies_trending",
            title = "Películas y Tendencias",
            items = listOf(
                MediaItem(
                    id = "movie_dune",
                    title = "Duna: Parte Dos",
                    subtitle = "El viaje heroico de Paul Atreides",
                    description = "Paul Atreides se une a Chani y a los Fremen mientras busca venganza contra los conspiradores que destruyeron a su familia.",
                    year = "2024",
                    rating = "13+",
                    duration = "2h 46m",
                    score = "9.3",
                    badges = listOf("4K UHD", "IMDb 8.9", "Dolby Atmos"),
                    backdropUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=600&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                    isPoster = true
                ),
                MediaItem(
                    id = "movie_shogun",
                    title = "Shōgun",
                    subtitle = "Honor, destino y guerra feudal",
                    description = "En el siglo XVII, un náufrago inglés llega a las costas japonesas y se convierte en pieza clave en la lucha del señor Toranaga.",
                    year = "2024",
                    rating = "18+",
                    duration = "1 Temporada",
                    score = "9.2",
                    badges = listOf("Exclusivo", "4K HDR", "5.1"),
                    backdropUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?auto=format&fit=crop&w=600&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    isPoster = true
                ),
                MediaItem(
                    id = "movie_oppenheimer",
                    title = "Oppenheimer",
                    subtitle = "El hombre que cambió el mundo",
                    description = "La historia del físico J. Robert Oppenheimer y su trascendental papel en el desarrollo de la bomba atómica.",
                    year = "2023",
                    rating = "16+",
                    duration = "3h 00m",
                    score = "9.4",
                    badges = listOf("Oscar Winner", "4K UHD", "Dolby Atmos"),
                    backdropUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?auto=format&fit=crop&w=600&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                    isPoster = true
                ),
                MediaItem(
                    id = "movie_bladerunner",
                    title = "Blade Runner 2049",
                    subtitle = "Hay recuerdos que no deberían existir",
                    description = "Treinta años después de los eventos del primer film, un nuevo blade runner descubre un secreto largamente enterrado.",
                    year = "2022",
                    rating = "16+",
                    duration = "2h 44m",
                    score = "8.9",
                    badges = listOf("4K HDR", "IMDb 8.0", "Dolby Vision"),
                    backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=600&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                    isPoster = true
                ),
                MediaItem(
                    id = "movie_interstellar",
                    title = "Interstellar",
                    subtitle = "El fin de la Tierra no será el fin de la humanidad",
                    description = "Un grupo de exploradores espaciales viaja a través de un agujero de gusano para asegurar la supervivencia humana.",
                    year = "2021",
                    rating = "13+",
                    duration = "2h 49m",
                    score = "9.5",
                    badges = listOf("4K UHD", "IMDb 8.7", "Dolby Atmos"),
                    backdropUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=1920&q=85",
                    posterUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=600&q=80",
                    videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    isPoster = true
                )
            )
        )
    )
}

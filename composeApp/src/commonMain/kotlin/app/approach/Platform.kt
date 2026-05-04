package app.approach

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
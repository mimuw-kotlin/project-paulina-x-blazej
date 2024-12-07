package pl.edu.uw.juwenalia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package org.crys.gymrat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
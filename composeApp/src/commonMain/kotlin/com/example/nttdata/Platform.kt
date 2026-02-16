package com.example.nttdata

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
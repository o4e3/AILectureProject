package org.ral.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
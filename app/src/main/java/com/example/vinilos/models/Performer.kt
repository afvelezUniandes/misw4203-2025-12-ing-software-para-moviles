package com.example.vinilos.models

import java.util.Date

data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: Date? = null,
    val creationDate: Date? = null
)

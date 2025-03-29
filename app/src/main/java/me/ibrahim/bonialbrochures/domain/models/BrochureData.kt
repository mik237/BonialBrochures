package me.ibrahim.bonialbrochures.domain.models

data class BrochureData(
    val id: Long,
    val contentId: String,
    val title: String,
    val validFrom: String,
    val validUntil: String,
    val publishedFrom: String,
    val publishedUntil: String,
    val type: String,
    val brochureImage: String,
    val pageCount: Int,
    val publisherName: String? = null,
    val publisherId: String,
)

package me.ibrahim.bonialbrochures.data.mappers

import me.ibrahim.bonialbrochures.data.remote.dto.BrochureContent
import me.ibrahim.bonialbrochures.domain.models.BrochureData

fun BrochureContent.toBrochuresData(): BrochureData {
    return BrochureData(
        id = id,
        contentId = contentId,
        title = title,
        validFrom = validFrom,
        validUntil = validUntil,
        publishedFrom = publishedFrom,
        publishedUntil = publishedUntil,
        type = type,
        brochureImage = brochureImage,
        pageCount = pageCount,
        publisherName = publisher.name,
        publisherId = publisher.id
    )
}
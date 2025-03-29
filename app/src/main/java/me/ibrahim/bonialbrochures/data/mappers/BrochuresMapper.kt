package me.ibrahim.bonialbrochures.data.mappers

import me.ibrahim.bonialbrochures.data.BrochureType
import me.ibrahim.bonialbrochures.data.remote.dto.BrochureContent
import me.ibrahim.bonialbrochures.domain.models.BrochureData

fun BrochureContent.toBrochuresData(contentType: String = BrochureType.Brochure.type): BrochureData {
    return BrochureData(
        id = id,
        contentId = contentId,
        title = title,
        validFrom = validFrom,
        validUntil = validUntil,
        publishedFrom = publishedFrom,
        publishedUntil = publishedUntil,
        contentType = contentType,
        brochureImage = brochureImage,
        pageCount = pageCount,
        publisherName = publisher.name,
        publisherId = publisher.id
    )
}
package me.ibrahim.bonialbrochures.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class BrochuresResponse(
    @SerializedName("_embedded")
    val embedded: Embedded? = Embedded(),
    @SerializedName("_links")
    val links: Links? = Links(),
    @SerializedName("page")
    val page: Page? = Page()
) : Parcelable


@Keep
@Parcelize
data class Embedded(
    @SerializedName("contents")
    val contents: List<ResponseContent>? = listOf()
) : Parcelable

@Keep
@Parcelize
data class ResponseContent(
    @SerializedName("adFormat")
    val adFormat: String? = "",
    @SerializedName("content")
    val content: ContentWrapper,
    @SerializedName("contentFormatSource")
    val contentFormatSource: String? = "",
    @SerializedName("contentType")
    val contentType: String = "",
    @SerializedName("placement")
    val placement: String? = ""
) : Parcelable

@Keep
@Parcelize
data class ContentWrapper(
    val listContent: List<ContentItem>?,  // For list case
    val singleContent: BrochureContent?   // For single object case
) : Parcelable

@Keep
@Parcelize
data class ContentItem(
    val content: ContentDetails,
) : Parcelable

@Keep
@Parcelize
data class ContentDetails(
    val id: String,
    val publisher: Publisher,
    val publishedFrom: String,
    val publishedUntil: String,
    val clickUrl: String?,
    val imageUrl: String?
) : Parcelable

@Keep
@Parcelize
data class BrochureContent(
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
    val publisher: Publisher,
    val contentBadges: List<ContentBadge>,
    val distance: Double,
    val hideValidityDate: Boolean,
    val closestStore: Store?
) : Parcelable

@Keep
@Parcelize
data class Publisher(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null
) : Parcelable

@Keep
@Parcelize
data class ContentBadge(val name: String) : Parcelable

@Keep
@Parcelize
data class Store(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val street: String,
    val streetNumber: String,
    val zip: String,
    val city: String
) : Parcelable


@Keep
@Parcelize
data class Links(
    @SerializedName("self")
    val self: Self? = null
) : Parcelable

@Keep
@Parcelize
data class Page(
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("totalElements")
    val totalElements: Int? = null,
    @SerializedName("totalPages")
    val totalPages: Int? = null
) : Parcelable


@Keep
@Parcelize
data class Self(
    @SerializedName("href")
    val href: String? = null
) : Parcelable


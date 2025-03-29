package me.ibrahim.bonialbrochures.data.utils

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import me.ibrahim.bonialbrochures.data.remote.dto.BrochureContent
import me.ibrahim.bonialbrochures.data.remote.dto.ContentItem
import me.ibrahim.bonialbrochures.data.remote.dto.ContentWrapper
import java.lang.reflect.Type

class ContentDeserializer : JsonDeserializer<ContentWrapper> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ContentWrapper? {
        return if (json.isJsonArray) {
            ContentWrapper(
                singleContent = null,
                listContent = Gson().fromJson(json, object : TypeToken<List<ContentItem>>() {}.type)
            )
        } else {
            ContentWrapper(
                listContent = null,
                singleContent = Gson().fromJson(json, BrochureContent::class.java)
            )
        }
    }

}
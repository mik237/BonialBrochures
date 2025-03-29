package me.ibrahim.bonialbrochures.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ibrahim.bonialbrochures.BuildConfig
import me.ibrahim.bonialbrochures.data.remote.api.BonialApi
import me.ibrahim.bonialbrochures.data.remote.dto.ContentWrapper
import me.ibrahim.bonialbrochures.data.utils.ContentDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideContentDeserializer(): ContentDeserializer {
        return ContentDeserializer()
    }

    @Provides
    @Singleton
    fun provideGson(deserializer: ContentDeserializer): Gson {
        val gson = GsonBuilder()
            .registerTypeAdapter(ContentWrapper::class.java, deserializer)
            .create()
        return gson
    }

    @Provides
    @Singleton
    fun provideBonialApi(gson: Gson): BonialApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(BonialApi::class.java)
    }
}
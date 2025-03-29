package me.ibrahim.bonialbrochures.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ibrahim.bonialbrochures.data.remote.api.BonialApi
import me.ibrahim.bonialbrochures.data.repositories.BrochuresRepositoryImpl
import me.ibrahim.bonialbrochures.domain.repositories.BrochuresRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBrochuresRepository(api: BonialApi): BrochuresRepository {
        return BrochuresRepositoryImpl(api = api)
    }
}
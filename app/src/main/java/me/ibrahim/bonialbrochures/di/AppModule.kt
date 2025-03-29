package me.ibrahim.bonialbrochures.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ibrahim.bonialbrochures.data.remote.api.BonialApi
import me.ibrahim.bonialbrochures.data.repositories.BrochuresRepositoryImpl
import me.ibrahim.bonialbrochures.data.utils.ConnectionManagerImpl
import me.ibrahim.bonialbrochures.domain.repositories.BrochuresRepository
import me.ibrahim.bonialbrochures.domain.utils.ConnectionManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBrochuresRepository(api: BonialApi, connectionManager: ConnectionManager): BrochuresRepository {
        return BrochuresRepositoryImpl(api = api, connectionManager = connectionManager)
    }

    @Provides
    @Singleton
    fun provideConnectionManager(app: Application): ConnectionManager = ConnectionManagerImpl(app)
}
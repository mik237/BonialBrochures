package me.ibrahim.bonialbrochures.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.ibrahim.bonialbrochures.domain.models.BrochureData

interface BrochuresRepository {
    fun getBrochures(): Flow<PagingData<BrochureData>>
}
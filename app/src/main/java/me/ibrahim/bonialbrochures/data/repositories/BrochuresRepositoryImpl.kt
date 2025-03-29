package me.ibrahim.bonialbrochures.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.ibrahim.bonialbrochures.data.remote.api.BonialApi
import me.ibrahim.bonialbrochures.data.remote.pagingSource.BrochurePagingSource
import me.ibrahim.bonialbrochures.domain.models.BrochureData
import me.ibrahim.bonialbrochures.domain.repositories.BrochuresRepository

class BrochuresRepositoryImpl(
    private val api: BonialApi
) : BrochuresRepository {

    override fun getBrochures(): Flow<PagingData<BrochureData>> {
        return Pager(
            config = PagingConfig(pageSize = 200),
            pagingSourceFactory = {
                BrochurePagingSource(api = api)
            }).flow
    }

}
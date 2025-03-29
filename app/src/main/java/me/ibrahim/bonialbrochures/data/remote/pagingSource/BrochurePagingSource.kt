package me.ibrahim.bonialbrochures.data.remote.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.ibrahim.bonialbrochures.data.BrochureType
import me.ibrahim.bonialbrochures.data.mappers.toBrochuresData
import me.ibrahim.bonialbrochures.data.remote.api.BonialApi
import me.ibrahim.bonialbrochures.domain.models.BrochureData
import me.ibrahim.bonialbrochures.domain.utils.ConnectionManager

class BrochurePagingSource(
    private val api: BonialApi,
    private val connectionManager: ConnectionManager
) : PagingSource<Int, BrochureData>() {

    override fun getRefreshKey(state: PagingState<Int, BrochureData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BrochureData> {
        val currentPage = params.key ?: 0

        return try {
            if (connectionManager.isConnected()) {
                val response = api.getBrochures(pageNumber = currentPage)
                val totalPages = response.page?.totalPages ?: 1

                val brochuresData = response.embedded?.contents
                    ?.filter { it.contentType in listOf(BrochureType.Brochure.type, BrochureType.BrochurePremium.type) }
                    ?.mapNotNull {
                        it.content.singleContent?.takeIf { it.distance <= 5.0 }?.toBrochuresData(it.contentType)
                    }

                LoadResult.Page(
                    data = brochuresData ?: emptyList(),
                    prevKey = if (currentPage == 0) null else currentPage - 1,
                    nextKey = if (currentPage < totalPages - 1) currentPage + 1 else null
                )
            } else {
                LoadResult.Error(Exception("No internet connection"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
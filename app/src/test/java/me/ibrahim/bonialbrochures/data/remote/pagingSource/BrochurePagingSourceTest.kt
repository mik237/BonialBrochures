package me.ibrahim.bonialbrochures.data.remote.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import me.ibrahim.bonialbrochures.data.BrochureType
import me.ibrahim.bonialbrochures.data.remote.api.BonialApi
import me.ibrahim.bonialbrochures.data.remote.dto.BrochureContent
import me.ibrahim.bonialbrochures.data.remote.dto.BrochuresResponse
import me.ibrahim.bonialbrochures.data.remote.dto.ContentWrapper
import me.ibrahim.bonialbrochures.data.remote.dto.Embedded
import me.ibrahim.bonialbrochures.data.remote.dto.Page
import me.ibrahim.bonialbrochures.data.remote.dto.Publisher
import me.ibrahim.bonialbrochures.data.remote.dto.ResponseContent
import me.ibrahim.bonialbrochures.domain.utils.ConnectionManager
import org.junit.Before
import org.junit.Test
import java.io.IOException

class BrochurePagingSourceTest {

    private lateinit var api: BonialApi
    private lateinit var connectionManager: ConnectionManager
    private lateinit var pagingSource: BrochurePagingSource

    @Before
    fun setUp() {
        api = mockk()
        connectionManager = mockk()
        pagingSource = BrochurePagingSource(api, connectionManager)
    }

    @Test
    fun `load returns Page when api call is successful`() = runTest {
        every { connectionManager.isConnected() } returns true
        coEvery { api.getBrochures(0) } returns BrochuresResponse(
            page = Page(totalElements = 50, totalPages = 1),
            embedded = Embedded(
                contents = listOf(
                    ResponseContent(
                        contentType = "brochure",
                        content = ContentWrapper(
                            listContent = null,
                            singleContent = BrochureContent(
                                title = "Test Brochure",
                                id = 1L,
                                contentId = "123",
                                validFrom = "2025-01-01",
                                validUntil = "2025-12-31",
                                publishedFrom = "2025-01-01",
                                publishedUntil = "2025-12-31",
                                type = BrochureType.Brochure.type,
                                brochureImage = "https://example.com/image.jpg",
                                pageCount = 10,
                                publisher = Publisher(id = "TestId", name = "Test Publisher"),
                                contentBadges = emptyList(),
                                distance = 4.5,
                                hideValidityDate = false,
                                closestStore = null
                            )
                        ),
                    )
                )
            )
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals(1, (result as PagingSource.LoadResult.Page).data.size)
        assertEquals("Test Brochure", result.data[0].title)
    }


    @Test
    fun `load returns empty page when api call is successful but no data is returned`() = runTest {
        every { connectionManager.isConnected() } returns true
        coEvery { api.getBrochures(0) } returns BrochuresResponse()

        val result = pagingSource.load(PagingSource.LoadParams.Refresh(key = 0, loadSize = 20, placeholdersEnabled = false))

        assertTrue(result is PagingSource.LoadResult.Page)
        assertTrue((result as PagingSource.LoadResult.Page).data.isEmpty())
    }

    @Test
    fun `load returns error when API call fails`() = runTest {
        // Given
        every { connectionManager.isConnected() } returns true
        coEvery { api.getBrochures(0) } throws IOException("Network error")

        // When
        val result = pagingSource.load(PagingSource.LoadParams.Refresh(0, 10, false))

        // Then
        assertTrue(result is LoadResult.Error)
        val error = result as LoadResult.Error
        assertTrue(error.throwable is IOException)
        assertEquals("Network error", error.throwable.message)
    }

    @Test
    fun `load returns error when no internet connection`() = runTest {
        // Given
        every { connectionManager.isConnected() } returns false

        // When
        val result = pagingSource.load(PagingSource.LoadParams.Refresh(0, 10, false))

        // Then
        assertTrue(result is LoadResult.Error)
        val error = result as LoadResult.Error
        assertEquals("No internet connection", error.throwable.message)
    }
}
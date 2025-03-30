package me.ibrahim.bonialbrochures.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import me.ibrahim.bonialbrochures.domain.utils.ConnectionManager
import org.junit.Before
import org.junit.Test


class ConnectionManagerImplTest {

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var network: Network
    private lateinit var networkCapabilities: NetworkCapabilities
    private lateinit var connectionManager: ConnectionManager

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        connectivityManager = mockk()
        network = mockk()
        networkCapabilities = mockk()
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager

        connectionManager = ConnectionManagerImpl(context)

    }

    @Test
    fun `isConnected returns true when device has internet connection`() {
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true

        val connected = connectionManager.isConnected()

        assertTrue(connected)
    }

    @Test
    fun `isConnected returns false when device has no internet connection`() {
        every { connectivityManager.activeNetwork } returns null
        val connected = connectionManager.isConnected()

        assertFalse(connected)
    }
}
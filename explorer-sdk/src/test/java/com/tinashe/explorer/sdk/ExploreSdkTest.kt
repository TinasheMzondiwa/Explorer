package com.tinashe.explorer.sdk

import android.content.Context
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class ExploreSdkTest {

    @Mock
    lateinit var mockContext: Context

    @Before
    fun setUp() {
        initMocks(this)
    }

    @Test
    fun get_repository_returns_non_null_value() {
        val repository = ExplorerSdk.getRepository(mockContext)

        assertNotNull(repository)
    }
}
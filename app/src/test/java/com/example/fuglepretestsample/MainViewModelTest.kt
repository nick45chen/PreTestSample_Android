package com.example.fuglepretestsample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fuglepretestsample.models.Symbols
import com.example.fuglepretestsample.models.SymbolsLast
import com.example.fuglepretestsample.repositories.IStocksRepository
import com.example.fuglepretestsample.utils.any
import com.example.fuglepretestsample.utils.argumentCaptor
import com.example.fuglepretestsample.utils.capture
import com.example.fuglepretestsample.viewmodels.MainViewModel
import com.example.lib_network.interfaces.IApiCallback
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    @Mock
    private lateinit var mockRepository: IStocksRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun getStocksLastTest() {
        val symbols = Symbols("A", "AA", "9999/99/99", false, "TYPE", "ID")
        val symbolsLast = SymbolsLast("A", 10f, 10, 0)
        // 呼叫SUT
        viewModel.getStocks()
        //
        val apiCallback = argumentCaptor<IApiCallback<List<Symbols>>>()
        // 驗證 mockRepository
        verify(mockRepository).getSymbols(capture(apiCallback))
        // 模擬成功後回調
        apiCallback.value.onSuccess(mutableListOf(symbols))

        val apiCallback2 = argumentCaptor<IApiCallback<List<SymbolsLast>>>()
        // 驗證是否有呼叫第二段API
        verify(mockRepository).getSymbolsLast(capture(apiCallback2), any())
        // 模擬成功後回調
        apiCallback2.value.onSuccess(mutableListOf(symbolsLast))
        // 驗證是否有一筆資料
        Assert.assertTrue(viewModel.stocksLiveData.value!!.size == 1)
    }
}
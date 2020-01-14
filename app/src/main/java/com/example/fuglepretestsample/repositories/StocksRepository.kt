package com.example.fuglepretestsample.repositories

import com.example.fuglepretestsample.api.StocksSevice
import com.example.fuglepretestsample.models.Symbols
import com.example.fuglepretestsample.models.SymbolsLast
import com.example.lib_network.RetrofitDelegate
import com.example.lib_network.interfaces.IApiCallback
import com.example.lib_network.transfromers.SchedulersTransformers
import io.reactivex.disposables.Disposable
import java.util.*

class StocksRepository : IStocksRepository {

    private val retrofitDelegate by lazy { RetrofitDelegate }
    private val disposables by lazy { ArrayList<Disposable>() }

    override fun getSymbols(callback: IApiCallback<List<Symbols>>) {
        callback.onStart()
        disposables.add(
            retrofitDelegate.create(StocksSevice::class.java)
                .getSymbols()
                .compose(SchedulersTransformers.applySchedulers())
                .subscribe(
                    {
                        callback.onSuccess(it)
                        callback.onCompleted()
                    },
                    {
                        callback.onFail(it)
                        callback.onCompleted()
                    }

                ))

    }

    override fun getSymbolsLast(callback: IApiCallback<List<SymbolsLast>>, vararg symbols: String) {
        // 空白參數的話，直接返回空數組
        if (symbols.isEmpty()) {
            callback.onSuccess(arrayListOf())
            callback.onCompleted()
            return
        }
        // 將字串串起來
        var args = ""
        symbols.forEach {
            if (args.isNotEmpty())
                args += ","
            args += it
        }

        callback.onStart()
        disposables.add(
            retrofitDelegate.create(StocksSevice::class.java)
                .getLast(symbols = args)
                .compose(SchedulersTransformers.applySchedulers())
                .subscribe(
                    {
                        callback.onSuccess(it)
                        callback.onCompleted()
                    },
                    {
                        callback.onFail(it)
                        callback.onCompleted()
                    }

                ))
    }

    override fun destroyAllEvent() {
        disposables.forEach {
            it.dispose()
        }
    }
}
package com.example.fuglepretestsample;

import com.example.fuglepretestsample.models.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StockReporter {

    private Repository repository;

    public StockReporter(Repository repository) {
        this.repository = repository;
    }

    /**
     * 查詢股票更新時間
     *
     * @param symbol 股市代號
     */
    public String getStockUpdateTime(String symbol) {
        Stock stock = repository.queryStockFromServer(symbol);
        return formatTime("");
    }

    /**
     * 格式化時間顯示方式
     *
     * @param dateString 日期字符串(yyyy-MM-dd)
     */
    private String formatTime(String dateString) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = fmt.parse(dateString);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return fmtOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    class Repository {
        /**
         * 從服務器取得股票資料
         *
         * @param symbol 股市代號
         */
        public Stock queryStockFromServer(String symbol) {
            // 模擬從 API 取得資料
            return new Stock(
                    "ATA CREATIVITY GLOBAL - ADR",
                    "AACG",
                    1.16f,
                    0);
        }
    }


//    public void use() {
//        StockReporter reporter = StockReporter(new Repository());
//
//        String updateTime = reporter.getStockUpdateTime("AACG");
//
//        System.out.println("AACG update time: " + updateTime);
//
//    }

}

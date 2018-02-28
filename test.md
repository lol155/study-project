1. 启动 | com.dustpan.hermes.data.`DataMain`#main
2. 启动店铺同步线程 | com.dustpan.hermes.data.DataMain#startShopInitService  
![慧打代码逻辑-201828173843](http://ovasdkxqr.bkt.clouddn.com/image/work/慧打代码逻辑-201828173843.png)
3. run方法 | com.dustpan.hermes.data.init.ShopInitService#run
    1. 同步订单 商品
        * 初始化本地 TaskQueue
        * 根据线程数量启动 ShopInitTask
            * 从队列中获取shopId
            * 重新查询 shopInfo
            * 初始化 | com.dustpan.hermes.data.init.ShopInitTask#initShop
                * 切换上下文
                * 处理店铺 | com.dustpan.hermes.data.init.ShopInitTask#doInitShop
                    * 获取或创建 shopSyncState 
                    * 更新同步记录数据情况
                    * 同步订单 | com.dustpan.hermes.core.service.sync.impl.TradeFetchServiceImpl#initShopTrade
                        * com.dustpan.hermes.core.service.sync.impl.TradeFetchServiceImpl#syncTrade
                    * 更新 shopSyncState
                    * 同步商品 | com.dustpan.hermes.core.service.sync.ItemFetchService#initShopItem
                        * com.dustpan.hermes.core.service.sync.impl.ItemFetchServiceImpl#syncItem
        * 将放入队列但是没被处理的重置成等待处理
        * 循环扫描修改时间在30天内的 且 sync_state = -1 的店铺 | com.dustpan.hermes.data.init.ShopInitService#doShopInitSync
            * 处理需要同步的店铺 | com.dustpan.hermes.data.init.ShopInitService#doInitSyncByShop
                * 更改shopInfo状态为 0    放入到初始化队列中,等待初始化
                * 将shopId加入到队列中
        * `ShopInitTask 对队列中的数据进行操作`
            * com.dustpan.hermes.data.init.ShopInitTask#run
                * com.dustpan.hermes.data.init.ShopInitTask#initShop
                    * com.dustpan.hermes.data.init.ShopInitTask#doInitShop
                        * com.dustpan.hermes.core.service.sync.TradeFetchService#initShopTrade
                            * com.dustpan.hermes.core.service.sync.impl.TradeFetchServiceImpl#syncTrade
                                ```java
                                    private boolean syncTrade(Shop shop, User user, Calendar startTime, Calendar endTime, ShopSyncState shopSyncState) {
                                        if (shop.getCreditLevel() == null || shop.getCreditLevel() <= 10) {
                                            return doSyncTrade(shop, user, startTime, endTime, shopSyncState); //小店铺按时间段同步
                                        } else if (shop.getCreditLevel() <= 15) {
                                            return doSyncTradeByInterval(shop, user, startTime, endTime, shopSyncState, 24 * 60 * 60 * 1000L); //中等店铺按天同步
                                        } else if (shop.getCreditLevel() <= 18) {
                                            return doSyncTradeByInterval(shop, user, startTime, endTime, shopSyncState, 15 * 60 * 1000L);  //大店铺按15分钟同步,按时间段淘宝API会超时
                                        } else if (shop.getCreditLevel() <= 20) {
                                            return doSyncTradeByInterval(shop, user, startTime, endTime, shopSyncState, 5 * 60 * 1000L); //超大店铺5分钟一次
                                        } else {
                                            return doSyncTradeByInterval(shop, user, startTime, endTime, shopSyncState, 60 * 1000L); //顶级店铺1分钟一次
                                        }
                                    }
                                ```
                        * com.dustpan.hermes.core.service.sync.ShopSyncService#initRdsSync

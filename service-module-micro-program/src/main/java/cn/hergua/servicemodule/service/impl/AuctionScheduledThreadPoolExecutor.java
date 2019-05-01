package cn.hergua.servicemodule.service.impl;


import cn.hergua.servicemodule.domain.Auction;
import cn.hergua.servicemodule.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AuctionScheduledThreadPoolExecutor {

    @Autowired
    AuctionService auctionService;

    @Autowired
    GoodsService goodsService;

    private ScheduledExecutorService scheduledThreadPool;

    private static List<Auction> auctionQueue = new ArrayList<>();

    /**
     * 为商品创建线程池用于计算商品的成交时间
     * 独立开出五条线程每微秒钟计算商品的拍卖状态
     */
    public AuctionScheduledThreadPoolExecutor() {
        scheduledThreadPool = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPool.schedule(this::scanAuctionSailStatus, 1, TimeUnit.MICROSECONDS);
    }

    /**
     * 在此方法内进行商品状态判断
     * @return 每个微妙对商品状态进行计算
     */
    @Bean
    public Runnable scanAuctionSailStatus(){
        return () -> {
            List<Auction> auctionQueue = AuctionScheduledThreadPoolExecutor.auctionQueue;
            auctionQueue.forEach(auction -> {
                if (auction.getTransactionTime().getTime() <= System.currentTimeMillis()){
                    Goods delayGoods = auction.getGoods();
                    delayGoods.setStatus("3");
                    goodsService.updateGoods(delayGoods);
                    auctionQueue.remove(auction);
                }
            });
        };
    }

    public void addAuctionToQueue(Auction auction){
        if (auction != null)
            AuctionScheduledThreadPoolExecutor.auctionQueue.add(auction);
    }



}

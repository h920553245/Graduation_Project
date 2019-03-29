package cn.hergua.servicemodule.service.impl;

import cn.hergua.servicemodule.domain.AuctionRecord;
import cn.hergua.servicemodule.repository.AuctionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Hergua
 * @Version 1.0
 * @Date 2019/3/28
 * <p>
 *
 * </p>
 */
@Service
public class AuctionRecordService {

    @Autowired
    AuctionRecordRepository repository;

    public void saveAuctionRecord(AuctionRecord record){
        repository.save(record);
    }

    public int queryCountOfPayer(Long auctionId){
        return repository.queryCountOfPayer(auctionId);
    }

}

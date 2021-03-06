package cn.hergua.servicemodule.service.impl;

import cn.hergua.servicemodule.domain.Goods;
import cn.hergua.servicemodule.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * @Author Hergua
 * @Version 1.0
 * @Date 2019/3/28
 * <p>
 *
 * </p>
 */
@Service
@Transactional
@Slf4j
public class GoodsService {

    @Autowired
    GoodsRepository repository;

    public void saveGoods(Goods goods){
        repository.save(goods);
    }

    public void updateGoods(Goods goods){
        repository.saveAndFlush(goods);
        log.info("update right now");
    }

    public void delGoods(Long id){
        repository.deleteById(id);
    }

    public Goods queryByGoodsId(Long goodsId){
        return repository.findById(goodsId).orElse(null);
    }

    public List<Goods> queryByUserId(Long userId){
        return repository.queryByUserId(userId);
    }

    public boolean queryGoodsIsForSail(Long goodsId, String queryStatus){
        try {
            String status = Objects.requireNonNull(repository.findById(goodsId).orElse(null)).getStatus();
            return status.equals(queryStatus);
        } catch (Exception e) {
            throw new RuntimeException("获取状态出错");
        }
    }


}

package com.jzo2o.foundations.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.request.ServeUpsertReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IServeService extends IService<Serve> {
    PageResult<ServeResDTO> page(ServePageQueryReqDTO servePageReqDTO);


    void batchAdd(List<ServeUpsertReqDTO> serveUpsertReqDTOS);

    Serve changePrice(Long id, BigDecimal price);

    Serve onSale(Long id);

    Serve offsale(Long id);

    Serve deleteServe(Long id);

    Serve onHot(Long id);

    Serve offHot(Long id);
}

package com.jzo2o.foundations.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.expcetions.ForbiddenOperationException;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.enums.FoundationStatusEnum;
import com.jzo2o.foundations.mapper.ServeMapper;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.request.ServeUpsertReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;
import com.jzo2o.foundations.service.IRegionService;
import com.jzo2o.foundations.service.IServeItemService;
import com.jzo2o.foundations.service.IServeService;
import com.jzo2o.mysql.utils.PageHelperUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServeServiceImpl extends ServiceImpl<ServeMapper, Serve> implements IServeService {
    @Resource
    private IRegionService regionService;
    @Resource
    private IServeItemService serveItemService;
    @Override
    public PageResult<ServeResDTO> page(ServePageQueryReqDTO servePageReqDTO) {
        return PageHelperUtils.selectPage(servePageReqDTO,
                () -> baseMapper.queryServeListByRegionId(servePageReqDTO.getRegionId()));
    }

    @Override
    public void batchAdd(List<ServeUpsertReqDTO> serveUpsertReqDTOS) {
        List<Serve> serves = new ArrayList<>(serveUpsertReqDTOS.size());
        for(ServeUpsertReqDTO serveUpsertReqDTO: serveUpsertReqDTOS){
            if(regionService.getById(serveUpsertReqDTO.getRegionId())==null||serveItemService.getById(serveUpsertReqDTO.getServeItemId())==null){
                throw new ForbiddenOperationException("区域id或服务id不存在");
            }
            if(serveItemService.getById(serveUpsertReqDTO.getServeItemId()).getActiveStatus()!= FoundationStatusEnum.ENABLE.getStatus()){
                throw new ForbiddenOperationException("该服务已下架");
            }
            Integer count = query().eq("serve_item_id", serveUpsertReqDTO.getServeItemId()).eq("region_id", serveUpsertReqDTO.getRegionId()).count();
            if(count>0){
                throw new ForbiddenOperationException("该区域已存在该服务");
            }
            Serve serve = BeanUtil.toBean(serveUpsertReqDTO, Serve.class);
            serve.setCityCode(regionService.getById(serveUpsertReqDTO.getRegionId()).getCityCode());
            serves.add(serve);
        }
        saveBatch(serves);
    }


}

package com.jzo2o.foundations.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.expcetions.CommonException;
import com.jzo2o.common.expcetions.ForbiddenOperationException;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.enums.FoundationStatusEnum;
import com.jzo2o.foundations.mapper.ServeMapper;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.domain.ServeItem;
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
import java.math.BigDecimal;
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

    @Override
    public Serve changePrice(Long id, BigDecimal price) {
        boolean update = update().eq("id", id).set("price", price).update();
        if(!update){
            throw new CommonException("修改价格失败");
        }
        return getById(id);

    }

    @Override
    public Serve onSale(Long id) {
        Serve byId = getById(id);
        if(byId==null){
            throw new ForbiddenOperationException("服务不存在");
        }
        Long serveItemId = byId.getServeItemId();
        ServeItem byId1 = serveItemService.getById(serveItemId);
        if(byId.getSaleStatus()== FoundationStatusEnum.ENABLE.getStatus()){
            throw new ForbiddenOperationException("服务已上架");
        }
        boolean update = update().eq("id", id).set("sale_status", FoundationStatusEnum.ENABLE.getStatus()).update();
        if(!update){
            throw new CommonException("上架失败");
        }
        return getById(id);
    }

    @Override
    public Serve offsale(Long id) {
        Serve byId = getById(id);
        if(byId==null){
            throw new ForbiddenOperationException("服务不存在");
        }
        if(byId.getSaleStatus()== FoundationStatusEnum.DISABLE.getStatus()){
            throw new ForbiddenOperationException("服务已下架");
        }
        boolean update = update().eq("id", id).set("sale_status", FoundationStatusEnum.DISABLE.getStatus()).set("is_hot",0).update();
        if(!update){
            throw new CommonException("下架失败");
        }
        return byId;
    }

    @Override
    public Serve deleteServe(Long id) {
        Serve byId = getById(id);
        if(byId==null){
            throw new ForbiddenOperationException("服务不存在");
        }
        if(byId.getSaleStatus()!= FoundationStatusEnum.INIT.getStatus()){
            throw new ForbiddenOperationException("服务无法删除");
        }
        boolean removeById = removeById(id);
        if(!removeById){
            throw new CommonException("删除失败");
        }
        return byId;
    }

    @Override
    public Serve onHot(Long id) {
        Serve byId = getById(id);
        if(byId==null){
            throw new ForbiddenOperationException("服务不存在");
        }
        if(byId.getSaleStatus()== FoundationStatusEnum.INIT.getStatus()||byId.getSaleStatus()== FoundationStatusEnum.DISABLE.getStatus()){
            throw new ForbiddenOperationException("服务未上架");
        }
        if(byId.getIsHot()==1){
            throw new ForbiddenOperationException("服务已设为热门");
        }
        boolean update = update().eq("id", id).eq("is_hot",0).set("is_hot", 1).set("hot_time_stamp", System.currentTimeMillis()).update();
        if(!update){
            throw new CommonException("设为热门失败");
        }
        return getById(id);

    }

    @Override
    public Serve offHot(Long id) {
        Serve byId = getById(id);
        if(byId==null){
            throw new ForbiddenOperationException("服务不存在");
        }
        if(byId.getIsHot()==0){
            throw new ForbiddenOperationException("已取消热门");
        }
        boolean update = update().eq("id", id).eq("is_hot",1).set("is_hot", 0).update();
        if(!update){
            throw new CommonException("取消热门失败");
        }
        return getById(id);
    }


}

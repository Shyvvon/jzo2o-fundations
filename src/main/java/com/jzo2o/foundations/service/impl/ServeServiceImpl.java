package com.jzo2o.foundations.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.mapper.ServeMapper;
import com.jzo2o.foundations.model.domain.Serve;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.request.ServeUpsertReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;
import com.jzo2o.foundations.service.IRegionService;
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
    @Override
    public PageResult<ServeResDTO> page(ServePageQueryReqDTO servePageReqDTO) {
        return PageHelperUtils.selectPage(servePageReqDTO,
                () -> baseMapper.queryServeListByRegionId(servePageReqDTO.getRegionId()));
    }




}

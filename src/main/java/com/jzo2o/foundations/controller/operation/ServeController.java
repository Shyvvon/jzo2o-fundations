package com.jzo2o.foundations.controller.operation;

import com.jzo2o.common.model.PageResult;
import com.jzo2o.foundations.model.dto.request.ServePageQueryReqDTO;
import com.jzo2o.foundations.model.dto.request.ServeUpsertReqDTO;
import com.jzo2o.foundations.model.dto.response.ServeResDTO;
import com.jzo2o.foundations.service.IServeService;
import com.jzo2o.foundations.service.IServeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("operationServeController")
@RequestMapping(value = "/operation/serve")
@Api(tags ="运营端-区域服务相关接口")
public class ServeController {
    @Resource
    private IServeService serveService;
    @GetMapping("/page")
    @ApiOperation("区域服务分页查询")
    public PageResult<ServeResDTO> page(ServePageQueryReqDTO servePageReqDTO){
        return serveService.page(servePageReqDTO);
    }

    @PostMapping("/batch")
    @ApiOperation("区域服务批量新增")
    public void batchAdd(@RequestBody List<ServeUpsertReqDTO> serveUpsertReqDTOS){
        serveService.batchAdd(serveUpsertReqDTOS);
    }
}

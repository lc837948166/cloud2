package com.xw.cloud.controller;

import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.service.MachineService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Machine")
@Api(tags = {"物理坤管理"},description = "用于处理物理坤信息的接口")
public class MachineController {
    @Resource
    private MachineService machineService;

    @GetMapping("/getMachineInfo")
    @ApiOperation(value= "查询物理坤信息", notes= "用于查询物理坤信息的接口")
    public CommentResp getMachineInfo() {
        return new CommentResp(true, machineService.getMachineInfo(), "");
    }
}



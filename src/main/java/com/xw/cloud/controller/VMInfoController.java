package com.xw.cloud.controller;

import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.bean.VMInfo2;
import com.xw.cloud.inter.OperationLogDesc;
import com.xw.cloud.mapper.VmMapper;
import com.xw.cloud.service.LibvirtService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Api(tags = "虚拟机信息", description = "提供虚拟机信息")
@CrossOrigin
@Controller
@RequestMapping("/VMInfo")
public class VMInfoController {

    @Resource
    private VmMapper vmMapper;

    @Resource(name = "libvirtService")
    private LibvirtService libvirtService;
    //通过id得到用户信息
    @RequestMapping(value = "/getVMInfo/{name}", method = RequestMethod.GET)
    public String getUser(@PathVariable String name){
        return vmMapper.selectById(name).toString();
    }
    //通过id删除用户信息
    @RequestMapping(value = "/delete/{name}",method = RequestMethod.DELETE)
    @ResponseBody
    public CommentResp delete(@PathVariable("name") String name){
        System.out.println(name);
        int result = vmMapper.deleteById(name);
        if(result >= 1){
            return new CommentResp(true, null,"删除成功");
        }else{
            return new CommentResp(false, null,"删除失败");
        }
    }
    //更改用户信息
    @RequestMapping(value = "/updateip/{serverip:.*}",method = RequestMethod.GET)
    @ResponseBody
    @OperationLogDesc(module = "虚拟机信息管理", events = "虚拟机信息更新")
    public CommentResp updateip(@PathVariable("serverip") String serverip) throws IOException {
        libvirtService.getallVMip(serverip);
            return new CommentResp(true, null,"更新成功");
    }

    //插入用户信息
    @RequestMapping(value = "/insert")
    @ResponseBody
    @OperationLogDesc(module = "虚拟机信息管理", events = "添加虚拟机信息")
    public CommentResp insert(@RequestBody VMInfo2 temp) {
        int result = vmMapper.insert(temp);
        if (result >= 1) {
            return new CommentResp(true, null,"插入成功");
        } else {
            return new CommentResp(false, null,"插入失败");
        }
    }

    //查询所有用户的信息
    @RequestMapping(value = "/selectAll")
    @ResponseBody   //理解为：单独作为响应体，这里不调用实体类的toString方法
    public CommentResp  listUser(){
        List<VMInfo2> tempList = vmMapper.selectList(null);
        return new CommentResp(true, tempList,"");
    }



}


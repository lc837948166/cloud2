package com.xw.cloud.controller;

import com.xw.cloud.Utils.CommentResp;
import com.xw.cloud.bean.*;
import com.xw.cloud.service.RemoteVMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "虚拟化资源管理", description = "管理虚拟机、快照和存储池等虚拟化资源")
@CrossOrigin
@Controller
@RequestMapping("/remoteVM")
public class RemoteVMController {

    @Resource(name = "remoteVMService")
    private RemoteVMService remoteVMService;

    private static String ip;

    @GetMapping("/getIP")
    @ResponseBody
    public void getIP(@RequestParam("ipaddr") String ipaddr) {
        System.out.println("enter");
        ip=ipaddr;
        System.out.println(ip);
    }


    @ApiOperation(value = "获取虚拟机列表", notes = "列出所有的虚拟机")
    @ResponseBody
    @GetMapping("/getVMList")
    public List<Virtual> getVMList() {
        return remoteVMService.getVirtualList(ip);

    }

    @ApiOperation(value = "获取虚拟机状态", notes = "列出虚拟机状态")
    @ResponseBody
    @GetMapping("/getVMstat")
    public Virtual getVMstat(@RequestParam("name") String name,@RequestParam("ipaddr") String ipaddr) {
        return remoteVMService.getVMstat(name,ipaddr);
    }

    @ApiOperation(value = "获取虚拟机指标列表", notes = "列出所有的虚拟机指标")
    @ResponseBody
    @GetMapping("/getVMIndexList")
    public List<Virtual> getVMIndexList() {
        return remoteVMService.getIndexList(ip);
    }

    @ApiOperation(value = "开启/关闭网络", notes = "根据提供的网络状态开启或关闭网络")
    @RequestMapping("/openOrCloseNetWork")
    public String openOrCloseNetWork(@RequestParam("netState") String netState) {
        if ("on".equals(netState)) remoteVMService.closeNetWork(ip);
        if ("off".equals(netState)) remoteVMService.openNetWork(ip);
        return "redirect:main";
    }

    @ApiOperation(value = "启动虚拟机", notes = "根据虚拟机名称启动虚拟机")
    @SneakyThrows
    @RequestMapping("/initiate/{name}")
    @ResponseBody
    public CommentResp initiateVirtual(@PathVariable("name") String name) {
        remoteVMService.initiateDomainByName(name,ip);
        return new CommentResp(true, null,"启动成功");
    }

    @ApiOperation(value = "挂起虚拟机", notes = "根据虚拟机名称挂起虚拟机")
    @SneakyThrows
    @RequestMapping("/suspended/{name}")
    @ResponseBody
    public CommentResp suspendedVirtual(@PathVariable("name") String name) {
        remoteVMService.suspendedDomainName(name,ip);
        return new CommentResp(true, null,"挂起成功");
    }

    @ApiOperation(value = "恢复虚拟机", notes = "根据虚拟机名称恢复虚拟机")
    @SneakyThrows
    @RequestMapping("/resume/{name}")
    @ResponseBody
    public CommentResp resumeVirtual(@PathVariable("name") String name) {
        remoteVMService.resumeDomainByName(name,ip);
        return new CommentResp(true, null,"还原成功");
    }

    @ApiOperation(value = "恢复虚拟机", notes = "根据虚拟机名称恢复虚拟机")
    @SneakyThrows
    @RequestMapping("/save")
    public String saveVirtual(@RequestParam("name") String name) {
        remoteVMService.saveDomainByName(name,ip);
        return "redirect:main";
    }

    @ApiOperation(value = "恢复虚拟机", notes = "根据虚拟机名称恢复虚拟机")
    @SneakyThrows
    @RequestMapping("/restore")
    public String restoreVirtual(@RequestParam("name") String name) {
        remoteVMService.restoreDomainByName(name,ip);
        return "redirect:main";
    }

    @ApiOperation(value = "关闭虚拟机", notes = "正常关闭指定的虚拟机")
    @SneakyThrows
    @RequestMapping("/shutdown/{name}")
    @ResponseBody
    public CommentResp shutdownVirtual(@PathVariable("name") String name) {
        remoteVMService.shutdownDomainByName(name,ip);
        return new CommentResp(true, null,"关机成功");
    }

    @ApiOperation(value = "强制关闭虚拟机", notes = "强制关闭指定的虚拟机")
    @SneakyThrows
    @RequestMapping("/shutdownMust/{name}")
    @ResponseBody
    public CommentResp shutdownMustVirtual(@PathVariable("name") String name) {
        remoteVMService.shutdownMustDomainByName(name,ip);
        return new CommentResp(true, null,"强行关机成功");
    }

    @ApiOperation(value = "重启虚拟机", notes = "重启指定的虚拟机")
    @SneakyThrows
    @RequestMapping("/reboot/{name}")
    @ResponseBody
    public CommentResp rebootVirtual(@PathVariable("name") String name) {
        remoteVMService.rebootDomainByName(name,ip);
        return new CommentResp(true, null,"正在重启");
    }

    @ApiOperation(value = "删除虚拟机", notes = "删除指定的虚拟机和其关联的镜像文件")
    @RequestMapping(value = "/delete/{name}",method = RequestMethod.DELETE)
    @ResponseBody
    public CommentResp deleteVirtual(@PathVariable("name") String name) {
        remoteVMService.deleteDomainByName(name,ip);
        remoteVMService.deleteImgFile(name + ".qcow2");
        return new CommentResp(true, null,name+"qcow2删除成功");
    }

    @ApiOperation(value = "跳转至添加虚拟机页面", notes = "返回添加虚拟机的界面")
    @RequestMapping("/toAddVirtual")
    public String toAddVirtual(Model model) {
        String netState = remoteVMService.getNetState(ip);
        model.addAttribute("netState", netState);
        return "addVirtual";
    }

//    @ResponseBody
//    @PostMapping(value = "/addVirtual")
//    public String addVirtual(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
//                             @RequestParam("memory") String memory, @RequestParam("cpuNum") String cpuNum,
//                             @RequestParam("OStype") String OStype) {
//        VM_create vmc = new VM_create();
//        vmc.setName(name);
//        vmc.setMemory(Long.parseLong(memory));
//        vmc.setCpuNum(Integer.parseInt(cpuNum));
//        vmc.setOStype(OStype);
//        libvirtService.addDomainByName(vmc);
//        libvirtService.addImgFile(vmc.getName(), file);
//        return "main";
//    }
        @ApiOperation(value = "添加虚拟机", notes = "根据提供的信息添加新的虚拟机")
        @ResponseBody
        @RequestMapping("/addVirtual")
        public CommentResp addVirtual(@RequestParam("ImgName") String ImgName, @RequestParam("name") String name,
                                      @RequestParam("memory") int memory, @RequestParam("cpuNum") int cpuNum,
                                      @RequestParam("OStype") String OStype) {
            VM_create vmc = new VM_create();
            vmc.setName(name);
            vmc.setMemory(memory);
            vmc.setCpuNum(cpuNum);
            vmc.setOStype(OStype);
            vmc.setImgName(ImgName);
            remoteVMService.addDomainByName(vmc,ip);
        //        libvirtService.addImgFile(vmc.getName(), file);
            return new CommentResp(true, null,"创建虚拟机成功");
        }



    @ApiOperation(value = "获取快照列表", notes = "根据虚拟机名称获取其快照列表")
    @RequestMapping("/getSnapshotList")
    public String getSnapshotList(@RequestParam("name") String name,
                                  Model model) {
        List<Snapshot> snapshotList = remoteVMService.getSnapshotListByName(name,ip);
        model.addAttribute("snapshotList", snapshotList);
        model.addAttribute("virtualName", name);
        return "snapshot";
    }

    @ApiOperation(value = "删除快照", notes = "删除指定虚拟机的特定快照")
    @SneakyThrows
    @RequestMapping("/deleteSnapshot")
    public String deleteSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        remoteVMService.deleteSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @ApiOperation(value = "还原快照", notes = "将指定虚拟机还原至特定快照的状态")
    @SneakyThrows
    @RequestMapping("/revertSnapshot")
    public String revertSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        remoteVMService.revertSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @ApiOperation(value = "创建快照", notes = "为指定虚拟机创建新的快照")
    @SneakyThrows
    @RequestMapping("/createSnapshot")
    public String createSnapshot(@RequestParam("virtualName") String virtualName,
                                 @RequestParam("snapshotName") String snapshotName) {
        remoteVMService.createSnapshot(virtualName, snapshotName);
        Thread.sleep(1000);
        return "redirect:/getSnapshotList?name=" + virtualName;
    }

    @ApiOperation(value = "存储池列表", notes = "获取所有存储池的列表")
    @RequestMapping("/storagePoolList")
    public String storagePoolList(Model model) {
        List<Storagepool> storagePoolList = remoteVMService.getStoragePoolList(ip);
        model.addAttribute("storagePoolList", storagePoolList);
        String netState = remoteVMService.getNetState(ip);
        model.addAttribute("netState", netState);
        return "storagepool";
    }

    @ApiOperation(value = "存储池列表", notes = "获取所有存储池的列表")
    @SneakyThrows
    @RequestMapping("/deleteStoragePool")
    public String deleteStoragePool(@RequestParam("name") String name) {
        remoteVMService.deleteStoragePool(name,ip);
        Thread.sleep(1000);
        return "redirect:/storagePoolList";
    }

    @ApiOperation(value = "跳转至创建存储池页面", notes = "返回创建存储池的界面")
    @RequestMapping("/toCreateStoragepool")
    public String toCreateStoragepool(Model model) {
        String netState = remoteVMService.getNetState(ip);
        model.addAttribute("netState", netState);
        return "addStoragepool";
    }

    @ApiOperation(value = "跳转至创建存储池页面", notes = "返回创建存储池的界面")
    @SneakyThrows
    @RequestMapping("/createStoragepool")
    public String createStoragepool(@RequestParam("storagepoolName") String name,
                                    @RequestParam("storagepoolPath") String url) {
        remoteVMService.createStoragepool(name, url,ip);
        Thread.sleep(1000);
        return "redirect:/storagePoolList";
    }

}

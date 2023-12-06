package com.xw.cloud.service.impl;


import com.xw.cloud.Utils.HardWareUtil;
import com.xw.cloud.bean.MachineInfo;
import com.xw.cloud.service.MachineService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service(value = "MachineService")
public class MachineServiceImpl implements MachineService {

    @SneakyThrows
    public MachineInfo getMachineInfo(){
        return HardWareUtil.getMachineInfo();
    };
}

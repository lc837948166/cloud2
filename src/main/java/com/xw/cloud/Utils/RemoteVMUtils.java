package com.xw.cloud.Utils;

import com.xw.cloud.bean.LibvirtConnect;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.libvirt.Connect;

@Log
public class RemoteVMUtils {

    private static Connect connect;

    // Connection
    @SneakyThrows
    public static Connect getConnection(String ipaddr) {

            connect = new Connect("qemu+ssh://"+ipaddr+"/system", false);
            log.info("Libvirt local connection successful" + "\n"
                    + "     连接URI: " + connect.getURI() + "\n"
                    + "     宿主机主机名: " + connect.getHostName() + "\n"
                    + "     宿主机剩余内存: " + connect.getFreeMemory() + "\n"
                    + "     宿主机最大cpu数量: " + connect.getMaxVcpus(null) + "\n"
                    + "     libvirt库版本号: " + connect.getLibVirVersion() + "\n"
                    + "     hypervisor名称: " + connect.getType()
            );

        return connect;
    }

    // ConnectionInfo
    @SneakyThrows
    public static LibvirtConnect getConnectionIo(String ipaddr) {
        Connect connect = getConnection(ipaddr);
        return LibvirtConnect.builder()
                .url(connect.getURI())
                .hostName(connect.getHostName())
                .libVirVersion(connect.getLibVirVersion())
                .hypervisorVersion(connect.getType())
                .build();
    }

    public static void main(String[] args,String ipaddr) {
        RemoteVMUtils.getConnection(ipaddr);
        System.out.println(RemoteVMUtils.getConnectionIo(ipaddr));
    }

}

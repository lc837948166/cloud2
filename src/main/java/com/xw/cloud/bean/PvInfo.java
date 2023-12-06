package com.xw.cloud.bean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "PV信息")
public class PvInfo {
    @ApiModelProperty(value = "PV名称", example = "my-pv")
    private String pvName;

    @ApiModelProperty(value = "PV路径", example = "/mnt/data")
    private String pvPath;

    @ApiModelProperty(value = "PV数量", example = "10")
    private String pvQuantity;

    @ApiModelProperty(value = "PV访问模式", example = "ReadWriteOnce")
    private String pvAccessMode;

    public String getPvName() {
        return pvName;
    }

    public void setPvName(String pvName) {
        this.pvName = pvName;
    }

    public String getPvPath() {
        return pvPath;
    }

    public void setPvPath(String pvPath) {
        this.pvPath = pvPath;
    }

    public String getPvQuantity() {
        return pvQuantity;
    }

    public void setPvQuantity(String pvQuantity) {
        this.pvQuantity = pvQuantity;
    }

    public String getPvAccessMode() {
        return pvAccessMode;
    }

    public void setPvAccessMode(String pvAccessMode) {
        this.pvAccessMode = pvAccessMode;
    }
}

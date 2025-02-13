package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import lombok.Data;

@Data
public class MainVehicleDownloadVO {
    //附件下载临时文件夹路径
    private String tempDictoryPath;

    //压缩包路径
    private String tempZipDictoryPath;

    //压缩包数据
    private byte[] zipContents;
}

package com.fmyd888.fengmao.module.information.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.module.information.common.vo.VehicleFileVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleDownloadVO;
import com.fmyd888.fengmao.module.information.enums.vehicle.VehicleFileTypeEnum;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.dal.mysql.file.FileMapper;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.system.dal.dataobject.dict.DictDataDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dict.DictDataMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: lmy
 * @Date: 2023/12/06 10:54
 * @Version: 1.0
 * @Description:
 */

public class VehicleUtils {

    //车头附件对应关系Map
    private static Map<String, String> mainVehicleFileDictDataMap;
    // 1、创建一个HashMap，用于存储车头附件枚举值，和对应mainVehicleRespVO的消费操作方法
    private static FileMapper fileMapper = null;
    private static DictDataMapper dictDataMapper = null;

    static {
        fileMapper = SpringUtil.getBean(FileMapper.class);
        dictDataMapper = SpringUtil.getBean(DictDataMapper.class);
        String vehicleCode = VehicleFileTypeEnum.VEHICLE_MAIN.getTypeName();
        String vehicleCode2 = VehicleFileTypeEnum.VEHICLE_TRAILER.getTypeName();
        LambdaQueryWrapper<DictDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(DictDataDO::getDictType, vehicleCode, vehicleCode2);
        List<DictDataDO> dictDataDOS = dictDataMapper.selectList(queryWrapper);
        // 添加逻辑，确保 dictDataDOS 不为 null
        if (dictDataDOS == null) {
            throw new RuntimeException("获取车头档案附加字典数据失败！");
        }
        // 使用toMap时提供合并函数，处理重复键的情况
        mainVehicleFileDictDataMap = dictDataDOS.stream().collect(
                Collectors.toMap(DictDataDO::getValue, DictDataDO::getLabel,
                        (existing, replacement) -> {
                            // 处理重复键的逻辑，这里简单地选择使用 replacement
                            return replacement;
                        }));
    }

    /**
     * 保存车头或车挂附件信息
     *
     * @param mainVehicleId
     * @param vehicleFileVO
     */
    public static void saveVehicleFile(Long mainVehicleId, VehicleFileVO vehicleFileVO) {

        //共用附件
        List<Long> taxDeclarationForm = vehicleFileVO.getTaxDeclarationForm();
        List<Long> purchaseInvoice = vehicleFileVO.getPurchaseInvoice();
        List<Long> otherFactoryDocuments = vehicleFileVO.getOtherFactoryDocuments();
        List<Long> attachments = vehicleFileVO.getAttachments();
        List<Long> vehicleRegistrationCertificate = vehicleFileVO.getVehicleRegistrationCertificate();
        List<Long> conformityCertificate = vehicleFileVO.getConformityCertificate();
        List<Long> roadTransportPermit = vehicleFileVO.getRoadTransportPermit();
        List<Long> factoryQualificationCertificate = vehicleFileVO.getFactoryQualificationCertificate();
        List<Long> registrationBook = vehicleFileVO.getRegistrationBook();
        List<Long> insuranceCertificate = vehicleFileVO.getInsuranceCertificate();
        if (CollectionUtils.isNotEmpty(taxDeclarationForm)) {
            String codeBusinessType = FileEnums.CT_10.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, taxDeclarationForm, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(purchaseInvoice)) {
            String codeBusinessType = FileEnums.CT_9.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, purchaseInvoice, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(otherFactoryDocuments)) {
            String codeBusinessType = FileEnums.CT_8.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, otherFactoryDocuments, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(attachments)) {
            String codeBusinessType = FileEnums.CT_7.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, attachments, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(vehicleRegistrationCertificate)) {
            String codeBusinessType = FileEnums.CT_5.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, vehicleRegistrationCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(conformityCertificate)) {
            String codeBusinessType = FileEnums.CT_4.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, conformityCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(roadTransportPermit)) {
            String codeBusinessType = FileEnums.CT_3.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, roadTransportPermit, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(factoryQualificationCertificate)) {
            String codeBusinessType = FileEnums.CT_2.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, factoryQualificationCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(registrationBook)) {
            String codeBusinessType = FileEnums.CT_1.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, registrationBook, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(insuranceCertificate)) {
            String codeBusinessType = FileEnums.CT_15.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, insuranceCertificate, codeBusinessType);
        }

        //车头附件
        List<Long> factoryPackingList = vehicleFileVO.getFactoryPackingList();
        List<Long> purchaseTaxCertificate = vehicleFileVO.getPurchaseTaxCertificate();
        List<Long> vehicleCheckRecord = vehicleFileVO.getVehicleCheckRecord();
        List<Long> gpsCertificate = vehicleFileVO.getGpsCertificate();
        List<Long> gradeAssessmentReport = vehicleFileVO.getGradeAssessmentReport();
        if (CollectionUtils.isNotEmpty(factoryPackingList)) {
            String codeBusinessType = FileEnums.CT_6.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, factoryPackingList, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(purchaseTaxCertificate)) {
            String codeBusinessType = FileEnums.CT_11.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, purchaseTaxCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(vehicleCheckRecord)) {
            String codeBusinessType = FileEnums.CT_12.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, vehicleCheckRecord, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(gpsCertificate)) {
            String codeBusinessType = FileEnums.CT_13.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, gpsCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(gradeAssessmentReport)) {
            String codeBusinessType = FileEnums.CT_14.getCodeBusinessType();
            updateFileSourceId(mainVehicleId, gradeAssessmentReport, codeBusinessType);
        }

    }

    /**
     * 保存车挂附件信息
     *
     * @param trailerId
     * @param vehicleFileVO
     */
    public static void saveVehicleFile2(Long trailerId, VehicleFileVO vehicleFileVO) {

        //共用附件
        List<Long> taxDeclarationForm = vehicleFileVO.getTaxDeclarationForm();
        List<Long> purchaseInvoice = vehicleFileVO.getPurchaseInvoice();
        List<Long> otherFactoryDocuments = vehicleFileVO.getOtherFactoryDocuments();
        List<Long> factoryPackingList = vehicleFileVO.getFactoryPackingList();
        List<Long> vehicleRegistrationCertificate = vehicleFileVO.getVehicleRegistrationCertificate();
        List<Long> conformityCertificate = vehicleFileVO.getConformityCertificate();
        List<Long> roadTransportPermit = vehicleFileVO.getRoadTransportPermit();
        List<Long> factoryQualificationCertificate = vehicleFileVO.getFactoryQualificationCertificate();
        List<Long> registrationBook = vehicleFileVO.getRegistrationBook();
        List<Long> purchaseTaxCertificate = vehicleFileVO.getPurchaseTaxCertificate();
        if (CollectionUtils.isNotEmpty(taxDeclarationForm)) {
            String codeBusinessType = FileEnums.CG_30.getCodeBusinessType();
            updateFileSourceId(trailerId, taxDeclarationForm, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(purchaseInvoice)) {
            String codeBusinessType = FileEnums.CG_29.getCodeBusinessType();
            updateFileSourceId(trailerId, purchaseInvoice, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(otherFactoryDocuments)) {
            String codeBusinessType = FileEnums.CG_28.getCodeBusinessType();
            updateFileSourceId(trailerId, otherFactoryDocuments, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(factoryPackingList)) {
            String codeBusinessType = FileEnums.CG_26.getCodeBusinessType();
            updateFileSourceId(trailerId, factoryPackingList, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(vehicleRegistrationCertificate)) {
            String codeBusinessType = FileEnums.CG_25.getCodeBusinessType();
            updateFileSourceId(trailerId, vehicleRegistrationCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(conformityCertificate)) {
            String codeBusinessType = FileEnums.CG_24.getCodeBusinessType();
            updateFileSourceId(trailerId, conformityCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(roadTransportPermit)) {
            String codeBusinessType = FileEnums.CG_23.getCodeBusinessType();
            updateFileSourceId(trailerId, roadTransportPermit, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(factoryQualificationCertificate)) {
            String codeBusinessType = FileEnums.CG_22.getCodeBusinessType();
            updateFileSourceId(trailerId, factoryQualificationCertificate, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(registrationBook)) {
            String codeBusinessType = FileEnums.CG_21.getCodeBusinessType();
            updateFileSourceId(trailerId, registrationBook, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(purchaseTaxCertificate)) {
            String codeBusinessType = FileEnums.CG_31.getCodeBusinessType();
            updateFileSourceId(trailerId, purchaseTaxCertificate, codeBusinessType);
        }

        List<Long> fileList1 = vehicleFileVO.getAttachment();
        List<Long> fileList2 = vehicleFileVO.getTransportationCheckRecord();
        List<Long> fileList3 = vehicleFileVO.getTaxPaymentCertificate();
        List<Long> fileList4 = vehicleFileVO.getTankInspectionReport();
        List<Long> fileList5 = vehicleFileVO.getVehicleSafetyInspectionReport();
        List<Long> fileList6 = vehicleFileVO.getOtherDocuments();
        if (CollectionUtils.isNotEmpty(fileList1)) {
            String codeBusinessType = FileEnums.CG_27.getCodeBusinessType();
            updateFileSourceId(trailerId, fileList1, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(fileList2)) {
            String codeBusinessType = FileEnums.CG_32.getCodeBusinessType();
            updateFileSourceId(trailerId, fileList2, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(fileList3)) {
            String codeBusinessType = FileEnums.CG_33.getCodeBusinessType();
            updateFileSourceId(trailerId, fileList3, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(fileList4)) {
            String codeBusinessType = FileEnums.CG_34.getCodeBusinessType();
            updateFileSourceId(trailerId, fileList4, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(fileList5)) {
            String codeBusinessType = FileEnums.CG_35.getCodeBusinessType();
            updateFileSourceId(trailerId, fileList5, codeBusinessType);
        }
        if (CollectionUtils.isNotEmpty(fileList6)) {
            String codeBusinessType = FileEnums.CG_36.getCodeBusinessType();
            updateFileSourceId(trailerId, fileList6, codeBusinessType);
        }
    }

    /**
     * 修改数据库文件记录中绑定的车辆档案id
     *
     * @param vehicleId 文件对应的车辆档案id
     * @param fileList  文件的id
     */
    private static void updateFileSourceId(Long vehicleId, List<Long> fileList, String type) {

        fileList.forEach(fileId -> {
            // 创建 UpdateWrapper 对象
            UpdateWrapper<FileDO> updateWrapper = new UpdateWrapper<>();
            //  设置更新条件
            updateWrapper.eq("id", fileId)
                    .eq("code_business_type", type);
            // 设置更新字段及其值
            updateWrapper.set("source_id", vehicleId);
            fileMapper.update(null, updateWrapper);
        });

        // 不属于fileList的修改
        QueryWrapper<FileDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn(CollectionUtils.isNotEmpty(fileList),"id", fileList);
        queryWrapper.eq("code_business_type", type);
        queryWrapper.eq("source_id", vehicleId);
        fileMapper.delete(queryWrapper);
    }

    // =======================车头车挂证件下载工具类方法===================

    /**
     * 构建zip压缩文件资源
     *
     * @param listMap 数据库数据关系结构集合对象，必要信息
     * @return 二进制数组输出流获取
     */
    public static MainVehicleDownloadVO createZip(Map<String, Map<String, List<FileDO>>> listMap) {
        // 临时目录用于存放下载的图片
        try {
            // 获取跨平台的默认临时目录
            String downloadedPath = System.getProperty("java.io.tmpdir");
            String fileLevel01 = UUID.randomUUID().toString();
            //附件下载所存放的文件夹位置
            String tempDirectory = Files.createTempDirectory(fileLevel01).toString();
            // 1、遍历车头的机动车登记编号Map
            listMap.forEach((licensePlate, fileTypeList) -> {
                // 添加车辆档案证件+当前日期文件夹
                String fileLevel02 = tempDirectory + File.separator + licensePlate;
                // 2、遍历车头的附件类型Map
                fileTypeList.forEach((fileType, fileList) -> {
                    AtomicInteger fileNumber = new AtomicInteger(1);
                    String fileTypeLabel = mainVehicleFileDictDataMap.get(fileType);
                    // 3、遍历车头的附件类型下的多个附件List集合
                    fileList.stream().forEach(fileDO -> {
                        // 4、创建UUID命名的子文件夹   UUID顶层文件夹/车牌号码
                        String imageSuffix = "jpg";
                        int lastDotIndex = fileDO.getUrl().lastIndexOf('.');
                        //动态获取文件后缀
                        if (lastDotIndex != -1) {
                            imageSuffix = fileDO.getUrl().substring(lastDotIndex + 1);
                        }
                        String fileLevel03 = licensePlate + "_" + fileTypeLabel + fileNumber.getAndIncrement();
                        //文件下载的全路径名
                        String fullName = fileLevel02 + File.separator + fileLevel03 + "." + imageSuffix;
                        //从服务器下载文件
                        downloadImage(fileDO.getUrl(), fullName);
                    });
                });
            });
            // 创建压缩包
            String zipFileName = tempDirectory + ".zip";
            createZipFile(tempDirectory, zipFileName);
            MainVehicleDownloadVO vo = new MainVehicleDownloadVO();
            vo.setTempDictoryPath(tempDirectory);
            vo.setTempZipDictoryPath(zipFileName);
            vo.setZipContents(Files.readAllBytes(Paths.get(zipFileName)));
            return vo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 才能给文件服务器下载附件
     *
     * @param imageUrl          附件服务器地址
     * @param downloadDirectory 目标下载地址，即下载到哪里
     * @throws IOException 异常
     */
    public static void downloadImage(String imageUrl, String downloadDirectory) {
        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Path targetPath = Paths.get(downloadDirectory);
        try (InputStream in = url.openStream()) {
            if (!Files.exists(targetPath.getParent())) {
                Files.createDirectories(targetPath.getParent());
            }
            Files.copy(in, Paths.get(downloadDirectory), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建压缩包
     *
     * @param sourceDirectory
     * @param zipFileName
     * @throws IOException
     */
    public static void createZipFile(String sourceDirectory, String zipFileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            Path sourcePath = Paths.get(sourceDirectory);
            Files.walk(sourcePath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
                        try {
                            zipOut.putNextEntry(zipEntry);
                            Files.copy(path, zipOut);
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    /**
     * 删除下载的临时附件寄生成的压缩包文件
     *
     * @param mainVehicleDownloadVO 路径信息
     */
    public static void deleteFiles(MainVehicleDownloadVO mainVehicleDownloadVO) {
        // 删除生成的压缩包
        try {
            Files.deleteIfExists(Paths.get(mainVehicleDownloadVO.getTempZipDictoryPath()));
            deleteDirectory(Paths.get(mainVehicleDownloadVO.getTempDictoryPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件夹
     *
     * @param directory 文件夹路径
     * @throws IOException 异常
     */
    public static void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    //======================车头车挂档案下载工具类方法======================================

    /**
     * 创建本地临时文件
     *
     * @return 创建成功的临时文件对象
     */
    public static File createTempFolder(String tempFolderName) {
        File tempFolder = new File(tempFolderName);  // TODO: 指定临时文件夹的路径
        tempFolder.mkdirs();
        return tempFolder;
    }

    /**
     * 压缩文件夹
     *
     * @param folder
     * @param tempFolderZip 指定压缩文件的路径（压缩文件名称）
     * @return 压缩文件夹对象
     */
    public static File zipFolder(File folder, String tempFolderZip) {
        File zipFile = new File(tempFolderZip);  // TODO:
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zipFile(folder, folder.getName(), zos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipFile;
    }

    /**
     * 压缩文件夹具体操作
     *
     * @param file
     * @param fileName
     * @param zos
     * @throws IOException
     */
    private static void zipFile(File file, String fileName, ZipOutputStream zos) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                zipFile(subFile, fileName + File.separator + subFile.getName(), zos);
            }
        } else {
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                zos.closeEntry();
            }
        }
    }

    /**
     * 将压缩文件写入响应输出流
     * @param zipFile  压缩文件
     * @param zipName  压缩文件名称命名
     * @param response  响应流
     * @throws IOException
     */
    public static void zipFileWriteToResponse(File zipFile,String zipName,HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/zip");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(zipName+".zip", "UTF-8"));
        // 将压缩文件写入响应输出流
        try (FileInputStream fis = new FileInputStream(zipFile);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
        }
    }

}

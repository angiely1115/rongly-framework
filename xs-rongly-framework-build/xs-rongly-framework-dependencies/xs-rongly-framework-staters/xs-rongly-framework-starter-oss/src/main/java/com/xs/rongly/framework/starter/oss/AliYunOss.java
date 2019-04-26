package com.xs.rongly.framework.starter.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: lvrongzhuan
 * @Description: aliyun oss API
 * @Date: 2019/4/4 15:16
 * @Version: 1.0
 * modified by:
 */
@Slf4j
public class AliYunOss {
    private String bucketName;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private long ossExpiretime;

    public AliYunOss(String bucketName, String endpoint, String accessKeyId, String accessKeySecret, long ossExpiretime) {
        this.bucketName = bucketName;
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.ossExpiretime = ossExpiretime;
    }
    /**
     * 获取阿里云OSS客户端对象
     */
    private  OSSClient getOSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 新建Bucket --Bucket权限:私有
     *
     * @return true 新建Bucket成功
     */
    public  boolean createBucket() {
        OSSClient client = getOSSClient();
        try {
            Bucket bucket = client.createBucket(bucketName);
            return bucketName.equals(bucket.getName());
        }finally {
            client.shutdown();
        }
    }

    /**
     * 删除Bucket
     */
    public  void deleteBucket() {
        OSSClient client = getOSSClient();
        client.deleteBucket(bucketName);
        client.shutdown();
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param file         上传文件
     * @param relativePath bucket下文件的路径：上传文件的目录
     * @return String       唯一MD5数字签名
     */
    public  String uploadObject2OSS(File file, String relativePath) throws IOException {
        String resultStr = null;
        OSSClient client = getOSSClient();
        PutObjectResult putResult;
        try (InputStream fileInputStream = new FileInputStream(file)) {
            String fileName = file.getName();
            long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileInputStream.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            // 不使用公共读
            /* if (isOfficeDocument(fileName)) {
                metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            }*/
            //上传文件
            putResult = client.putObject(bucketName, relativePath + fileName, fileInputStream, metadata);
            //解析结果
            resultStr = putResult.getETag();
            return resultStr;
        }finally {
            client.shutdown();
        }

    }


    /**
     * 向阿里云的OSS存储中创建文件夹
     *
     * @param folderName bucket下文件的路径：上传文件的目录
     * @return String       唯一MD5数字签名
     */
    public String createFolder(String folderName, String userName) {
        String resultStr = null;
        OSSClient client = getOSSClient();
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userName.getBytes())){
            PutObjectResult putResult = client.putObject(bucketName, folderName + "/",byteArrayInputStream);
            resultStr = putResult.getETag();
        } catch (Exception e) {
            log.error("向阿里云的OSS存储中创建文件夹异常：",e);
        }finally {
            client.shutdown();
        }
        return resultStr;
    }

    /**
     * 设置阿里云文件的访问控制权限为公共读
     *
     * @param key
     */
    public void updatePublicReadAccessControl(String key) {
        OSSClient client = getOSSClient();
        try {
            client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        } catch (Exception ex) {
            log.error("设置阿里云文件的访问控制权限为公共读异常：",ex);
        }finally {
            client.shutdown();
        }
    }

    /**
     * 根据key获取OSS服务器上的文件输入流
     *
     * @param relativePath bucket下文件的路径：上传文件的目录
     * @param fileName     文件名
     */
    public InputStream getOSS2InputStream(String relativePath, String fileName) {
        OSSClient client = getOSSClient();
        try {
            OSSObject ossObj = client.getObject(bucketName, String.format("%s/%s", relativePath, fileName));
            return ossObj.getObjectContent();
        }finally {
            client.shutdown();
        }

    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param relativePath bucket下文件的路径：上传文件的目录
     * @param fileName     文件名
     */
    public void deleteFile(String relativePath, String fileName) {
        OSSClient client = getOSSClient();
        try {
            client.deleteObject(bucketName, relativePath + fileName);
        }finally {
            client.shutdown();
        }

    }

    /**
     * 保存文件后获取签名地址URL
     *
     * @param key:例如：institutionRelativePath(bucket下文件的路径：上传文件的目录) + fileName
     * @param style
     * @return
     */
    public  String generatePresignedUri(String key, String style) {
        OSSClient client = getOSSClient();
        try {
            Date expires = new Date(System.currentTimeMillis() + ossExpiretime * 1000);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
            generatePresignedUrlRequest.setExpiration(expires);
            if (StringUtils.isNotBlank(style)) {
                generatePresignedUrlRequest.setProcess(style);
            }
            URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString().replace("http://", "https://");
        }finally {
            client.shutdown();
        }


    }


    /**
     *  前端通用操作阿里云需要的签名
     * @param relativePath
     * @return
     */
    public String getPolicySignature(String relativePath) {
        OSSClient client = getOSSClient();
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "");

            Map<String, Object> respMap = new LinkedHashMap<>();
            respMap.put("accessid", this.accessKeyId);
            respMap.put("accesssecret", this.accessKeySecret);
            respMap.put("relativePath", relativePath);
            respMap.put("bucket", bucketName);
            respMap.put("host", "http://" + bucketName + "." + this.endpoint);
            JSONObject response = new JSONObject(respMap);
            return response.toString();
        }finally {
            client.shutdown();
        }
    }

    public static boolean isOfficeDocument(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        if ("vsd".equalsIgnoreCase(fileExtension)) {

            return true;
        }
        if (fileExtension.toLowerCase().contains("pp") || fileExtension.toLowerCase().contains("po")) {

            return true;
        }
        if (fileExtension.toLowerCase().contains("doc") || fileExtension.toLowerCase().contains("dot")) {

            return true;
        }
        if (fileExtension.toLowerCase().contains("xl") || fileExtension.toLowerCase().contains("csv")) {

            return true;
        }
        return fileExtension.toLowerCase().contains("wps");
    }

    public  BufferedReader downloadFile(String relativePath, String fileName) throws IOException {
        OSSClient client = getOSSClient();
        OSSObject ossObject = client.getObject(bucketName, String.format("%s/%s", relativePath, fileName));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));){
            while (true) {
                String line = reader.readLine();
                if (line == null){ break;}

            }
            return reader;
        }
        finally {
            client.shutdown();
        }
    }

    /**
     * 下载文件到本地
     * @param filePath oss文件路径
     * @param file 本地文件
     * @return
     * @throws IOException
     */
    public  ObjectMetadata downloadFileByFile(String filePath,File file) {
        OSSClient ossClient = getOSSClient();
        try {
            return ossClient.getObject(new GetObjectRequest(bucketName, filePath), file);
        }
        finally {
            ossClient.shutdown();
        }
    }
    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        if ("bmp".equalsIgnoreCase(fileExtension)) {

            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(fileExtension)) {

            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension) || "png".equalsIgnoreCase(fileExtension)) {

            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(fileExtension)) {

            return "text/html";
        }
        if ("txt".equalsIgnoreCase(fileExtension)) {

            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(fileExtension)) {

            return "application/vnd.visio";
        }
        if (fileExtension.toLowerCase().contains("pp") || fileExtension.toLowerCase().contains("po")) {


            return "application/vnd.ms-powerpoint";
        }
        if (fileExtension.toLowerCase().contains("doc") || fileExtension.toLowerCase().contains("dot")) {

            return "application/msword";
        }
        if (fileExtension.toLowerCase().contains("xl") || fileExtension.toLowerCase().contains("csv")) {

            return "application/vnd.ms-excel";
        }
        if (fileExtension.toLowerCase().contains("wps")) {

            return "application/vnd.ms-works";
        }
        if ("xml".equalsIgnoreCase(fileExtension)) {

            return "text/xml";
        }
        if ("pdf".equalsIgnoreCase(fileExtension)) {

            return "application/pdf";
        }
        if ("pdx".equalsIgnoreCase(fileExtension)) {

            return "application/vnd.adobe.pdx";
        }
        if ("rar".equalsIgnoreCase(fileExtension) || "zip".equalsIgnoreCase(fileExtension)) {

            return "application/octet-stream";
        }
        return "text/html";
    }

}

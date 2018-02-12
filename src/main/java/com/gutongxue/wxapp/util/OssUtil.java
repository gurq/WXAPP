package com.gutongxue.wxapp.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.Instant;

/**
 * Created by Shadow on 2016/4/28.
 */
public class OssUtil {
    private static final String IMG_DOWNLOAD_DIR = File.separator + "gutongxue" + File.separator + "images" + File.separator;

    public static String getOSSUrl(String filepath, String format) {
        try {
            File file = new File(filepath);
            if (file.exists() && file.length() > 0) {
                String OssUrl = OssUtil.setOssInformation(TimeUtil.getTodayByFormat("yyyyMMddHHmmssSSS") + format, filepath);
                if (OssUrl != null && !OssUrl.equals("")) {
                    return OssUrl;
                }
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getOssUrl(MultipartHttpServletRequest request) {
        try {
            byte[] item;
            File saveDir;
            MultipartFile multipartFile = request.getFile("file");
            item = multipartFile.getBytes();
            String filepath = request.getSession().getServletContext().getRealPath("/resources/") + "file/";
            //文件保存位置
            saveDir = new File(filepath);

            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            String localFileName = Instant.now().toEpochMilli() + "_" + multipartFile.getOriginalFilename();
            String localFileUrl = filepath + localFileName;
            File file = new File(localFileUrl);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(item);
            fileOutputStream.close();
            if (file.exists() && file.length() > 0) {
                String OssUrl = OssUtil.setOssInformation(localFileName, localFileUrl);
                if (OssUrl != null && !OssUrl.equals("")) {
                    DeleteFileUtil.DeleteFolder(filepath);
                    return OssUrl;
                }
            }
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取上传到OSS的图片连接
     *
     * @param fileName       完整的文件名
     * @param uploadFilePath 文件的路径
     * @return 来自OSS的Url地址，如果返回值为“”，说明中间哪里出错了
     */
    private static String setOssInformation(String fileName, String uploadFilePath) {
        String uri = "";
        String month = TimeUtil.getTodayByFormat("yyyyMM");
        // 创建OSSClient对象
        OSSClient client = new OSSClient("oss-cn-hangzhou.aliyuncs.com", "LTAIlsjUAGJ49G5l", "xsHsEjYplgEvXhHTVimpzVCoTca5JD");
        //上传图片
        boolean isSuccess = uploadFile(client, "lveri", "images/" + month + "/" + fileName, uploadFilePath);

        if (isSuccess) {
            uri = "http://lveri.oss-cn-hangzhou.aliyuncs.com/images/" + month + "/" + fileName;
        }
        return uri;
    }

    /**
     * 将本地图片上传至OSS的方法
     *
     * @param client     OSS外网域名
     * @param bucketName bucket的名字
     * @param key        图片的名字
     * @param filePath   所上传图片的路径
     * @return boolean类型的成功与失败状态
     */
    private static boolean uploadFile(OSSClient client, String bucketName, String key, String filePath) {
        int MAX_TRY = 3;
        int downloadTurn = 0;
        boolean uploadSuccess = false;
        while (downloadTurn < MAX_TRY) {
            try {
                File file = new File(filePath);
                if ((!file.exists()) || file.length() == 0) {
                    uploadSuccess = false;
                    break;
                }
                ObjectMetadata objectMeta = new ObjectMetadata();
                objectMeta.setContentLength(file.length());
                // 可以在metadata中标记文件类型
                System.out.println("开始上传文件" + filePath);
                InputStream input = new FileInputStream(file);
                client.putObject(bucketName, key, input, objectMeta);
                System.out.println("上传成功!");
                uploadSuccess = true;
                break;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return uploadSuccess;
    }

    public static void deleteFileByOssUrl(String url) {
        url = url.replace("http://gutongxue.img-cn-beijing.aliyuncs.com/", "").replace("@!watermark", "");
        // 创建OSSClient对象
        OSSClient client = new OSSClient("oss-cn-beijing.aliyuncs.com", "LTAIlsjUAGJ49G5l", "xsHsEjYplgEvXhHTVimpzVCoTca5JD");
        if (client.doesObjectExist("gutongxue", url)) {
            client.deleteObject("gutongxue", url);
        }
    }
}

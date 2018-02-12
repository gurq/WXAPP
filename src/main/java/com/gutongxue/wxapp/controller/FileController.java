package com.gutongxue.wxapp.controller;

import com.gutongxue.wxapp.domain.Result;
import com.gutongxue.wxapp.util.GRQUtil;
import com.gutongxue.wxapp.util.OssUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Created by ShadowSaint on 2018/2/12
 */
@RestController
public class FileController {
    @RequestMapping(value = "/file",method = RequestMethod.POST)
    public Result postFile(HttpServletRequest request){
        Result result=new Result();
        try {
            String oss= OssUtil.getOssUrl((MultipartHttpServletRequest) request);
            if (GRQUtil.checkNull(oss)){
                result.setMessage("上传失败");
                result.setStatus(false);
            }else {
                result.setData(oss);
            }
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setStatus(false);
        }
        return result;
    }
}

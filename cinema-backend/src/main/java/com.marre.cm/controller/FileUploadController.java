package com.marre.cm.controller;

import com.marre.cm.common.file.FileUploadUtils;
import com.marre.cm.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author :marRE
 * @Description:封装图片上传请求
 * @Date :2024/4/1
 */

@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    /**
     * 用户图片上传
     *
     * @param file 文件
     * @return 结果
     * @throws IOException io异常
     */
    @PostMapping("/user")
    public R uploadUser(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.userPath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return R.success((Object) filename);
    }

    /**
     * 电影图片上传
     *
     * @param file 文件
     * @return 结果
     * @throws IOException io异常
     */
    @PostMapping("/movie")
    public R uploadMovie(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.moviePath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return R.success((Object) filename);
    }

    /**
     * 影院图片上传
     *
     * @param file 文件
     * @return 结果
     * @throws IOException io异常
     */
    @PostMapping("/cinema")
    public R uploadCinema(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.cinemaPath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return R.success((Object) filename);
    }

    /**
     * 上传
     *
     * @param file 文件
     * @return 结果
     * @throws IOException io异常
     */
    @PostMapping("/actor")
    public R uploadActor(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.actorPath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return R.success((Object) filename);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 结果
     */
    @RequestMapping("/delete")
    public R deletePicture(String filePath) {
        try {
            String path = ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + filePath;
            log.debug("删除文件路径为：" + path);
            boolean flag = FileUploadUtils.deleteFile(path);
            log.debug(flag ? "删除成功" : "删除失败");
        } catch (FileNotFoundException e) {
            log.error("删除文件不存在");
        } finally {
            return R.success();
        }
    }
}

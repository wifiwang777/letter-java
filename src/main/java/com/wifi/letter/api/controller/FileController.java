package com.wifi.letter.api.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.Gson;
import com.wifi.letter.api.jwt.JwtConfig;
import com.wifi.letter.api.model.User;
import com.wifi.letter.api.service.UserService;
import com.wifi.letter.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
public class FileController {
    @Autowired
    private Gson gson;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserService userService;

    @PostMapping("file")
    public String saveFile(@RequestHeader Map<String, String> header, @RequestParam("file") MultipartFile file) throws FileNotFoundException {
        if (file.isEmpty()) {
            return gson.toJson(Util.ErrMsg("文件内容为空"));
        }
        Integer uid = jwtConfig.getUidFromHeader(header);

        String fileName = file.getOriginalFilename();
        String rawFileName = StrUtil.subBefore(fileName, ".", true);
        String fileType = StrUtil.subAfter(fileName, ".", true);

        String finallyFileName = rawFileName +
                // "-" + DateUtil.current() +
                "." + fileType;
        String path = ResourceUtils.getURL("src/main/resources").getPath();
        String localFilePath = StrUtil.appendIfMissing(path + "static/assets/file", "/") + finallyFileName;
        try {
            file.transferTo(new File(localFilePath));
        } catch (IOException e) {
            System.out.println("【文件上传至本地】失败，绝对路径：{}" + localFilePath + e.toString());
            return gson.toJson(Util.ErrMsg("文件上传失败"));
        }

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("uid", uid);

        User updateUser = new User();
        updateUser.setAvatar(finallyFileName);
        boolean res = userService.update(updateUser, wrapper);
        if (res) {
            return gson.toJson(Util.SuccessMsg("上传成功:" + finallyFileName));
        } else {
            return gson.toJson(Util.SuccessMsg("上传失败:" + finallyFileName));
        }
    }
}

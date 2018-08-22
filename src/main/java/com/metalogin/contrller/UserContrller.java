package com.metalogin.contrller;

import com.metalogin.common.BaseData;
import com.metalogin.common.ResultGenerator;
import com.metalogin.entity.User;
import com.metalogin.exception.ApiException;
import com.metalogin.manager.UserManager;
import com.metalogin.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by: Yumira.
 * Created on: 2018/8/20-下午11:44.
 * Description:
 */
@RestController("/user")
@RequestMapping(value = "/user")
public class UserContrller {

    @Autowired
    private UserManager userManager;

    /**
     * 登录
     */
    @PostMapping("/login")
    public BaseData login(String address,String signature) throws ApiException {
        User user = userManager.verifyUser(address, signature);
        return  user != null ?
                ResultGenerator.genSuccessResult(user.getId()) :
                ResultGenerator.genFailResult("签名错误，登录失败");
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public BaseData register(String address) throws ApiException {
        return userManager.saveUser(new User(address)) != null ?
                ResultGenerator.genSuccessResult("注册成功"):
                ResultGenerator.genFailResult("注册失败");
    }

    /**
     * 随机数
     */
    @PostMapping("/nonce")
    public BaseData nonce(String address) throws ApiException {
        return ResultGenerator.genSuccessResult(userManager.updateNonce(new User(address)).getNonce());
    }



}

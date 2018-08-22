package com.metalogin.manager;

import com.metalogin.common.ResultCode;
import com.metalogin.crypto.DecodeMessage;
import com.metalogin.entity.User;
import com.metalogin.exception.ApiException;
import com.metalogin.repository.UserRepository;
import com.metalogin.utils.CommonUtil;
import com.metalogin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by: Yumira.
 * Created on: 2018/8/22-上午11:18.
 * Description:
 */
@Component
public class UserManager {

    @Autowired
    private UserRepository userRepository;


    public long getNonceByAddress(String address) throws ApiException {
        User user = findUserByAddress(address);
        if (user == null){
            throw new ApiException(ResultCode.FAIL,"用户不存在");
        }
        return user.getNonce();
    }

    public User findUserByAddress(String address) {
        User user = userRepository.findByAddress(address);
        return user;
    }

    public User saveUser(User user) throws ApiException {
        if (StringUtil.isEmpty(user.getAddress())){
            throw new ApiException(ResultCode.FAIL,"MetaMask请求失败，请稍候再试");
        }
        if (findUserByAddress(user.getAddress()) != null){
            throw new ApiException(ResultCode.FAIL,"该用户已存在，请直接登录");
        }
        user.setId(CommonUtil.generateUuid());
        return userRepository.save(user);
    }

    public User updateNonce(User user){
        user.setNonce(CommonUtil.generateNonce());
        try {
            userRepository.updateNonce(user.getNonce(),user.getAddress());//保存并更新
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public User verifyUser(String address,String signature) throws ApiException {
        User user = findUserByAddress(address);
        String result = DecodeMessage.verifySignature(user.getNonce() + "", signature);
        if (address.equals(result)){//登录成功，马上修改nonce
            return updateNonce(user);
        }
        return null;
    }

}

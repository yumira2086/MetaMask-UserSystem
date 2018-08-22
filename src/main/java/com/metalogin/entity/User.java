package com.metalogin.entity;

import com.metalogin.utils.CommonUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * Created by: Yumira.
 * Created on: 2018/8/20-下午10:56.
 * Description:
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * 用户索引
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "u_index")
    private long index;

    /**
     * uid
     */
    @Column(unique = true)
    private String id;

    /**
     * 以太坊网络address，相当于用户名
     */
    @Column(unique = true,nullable = false)
    private String address;

    /**
     * 一次性使用的随机数，用于校验签名，
     */
    private long nonce;

    public User() {
    }

    public User(String address) {
        this.address = address;
    }

    public User(String address, long nonce) {
        this.address = address;
        this.nonce = nonce;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "User{" +
                "index=" + index +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", nonce=" + nonce +
                '}';
    }
}

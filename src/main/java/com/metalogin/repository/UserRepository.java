package com.metalogin.repository;


import com.metalogin.entity.User;
import com.metalogin.utils.StringUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 查询用户
     */
    User findById(String id);

    User findByAddress(String address);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.nonce = ?1 where u.address = ?2")
    int updateNonce(long nonce,String address);

//    @Transactional
//    @Modifying
//    @Query(value = "update User u set u.nonce= ?1 where u.address= ?2")
//    int updateNonce(String nonce, String address);
}

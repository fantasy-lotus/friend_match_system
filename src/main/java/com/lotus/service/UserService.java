package com.lotus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lotus.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 31964
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-13 11:27:20
*/
public interface UserService extends IService<User> {

    Boolean userRegister(String tel, String pwd, String confirmPwd);

    User userLogin(String tel, String pwd, HttpServletRequest req);

    List<User> getUsersByTags(List<String> tags);

    User getCurrentUser(HttpServletRequest req);

    Boolean updateUser(User user, HttpServletRequest req);

    boolean isAdmin(User user);

    boolean isAdmin(HttpServletRequest request);
}

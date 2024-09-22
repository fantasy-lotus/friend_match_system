package com.lotus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lotus.common.BusinessException;
import com.lotus.common.ErrorCode;
import com.lotus.constant.UserConstant;
import com.lotus.mapper.UserMapper;
import com.lotus.pojo.User;
import com.lotus.service.UserService;
import com.lotus.utils.Encrypt;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Service
@NoArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean userRegister(String tel, String pwd, String confirmPwd) {
        //非空校验
        if (StringUtils.isAnyBlank(tel, pwd, confirmPwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"输入格式错误");
        }
        //电话号码格式校验
        String pattern = "^\\d*$";
        if (!(tel.length() == 11 && tel.matches(pattern))) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"电话号码长度错误");
        }
        //密码格式校验
        if (!pwd.equals(confirmPwd) || pwd.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }

        //账户查重
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        long res = count(queryWrapper.eq("tel", tel));
        if (res > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该电话号码已被注册");
        }

        //密码加密
        User targetUser = new User();
        targetUser.setTel(tel);
        targetUser.setPwd(Encrypt.encode(pwd));
        Boolean result = save(targetUser);
        if(result == Boolean.FALSE){
            throw new RuntimeException("数据插入失败");
        }
        return Boolean.TRUE;
    }

    @Override
    public User userLogin(String tel, String pwd, HttpServletRequest req) {
        //非空校验
        if (StringUtils.isAnyBlank(tel, pwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        //电话号码格式校验
        String pattern = "^\\d*$";
        if (!(tel.length() == 11 && tel.matches(pattern))) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"电话号码长度错误");
        }
        //密码格式校验
        if (pwd.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");
        }

        //身份校验
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tel", tel).eq("pwd", Encrypt.encode(pwd));
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码错误");
        }
        user.setPwd(null);
        HttpSession session = req.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return user;
    }

    @Override
    public List<User> getUsersByTags(List<String> tags) {
        if(CollectionUtils.isEmpty(tags)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        for (String tag : tags) {
            queryWrapper = queryWrapper.like("tags", tag);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(users)){
            throw new BusinessException(ErrorCode.NULL_ERROR,"没有找到数据");
        }
        users.forEach(user -> user.setPwd(null));
        return users;
    }

    @Override
    public User getCurrentUser(HttpServletRequest req) {
        if(req == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User user = (User) req.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(user == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        user.setPwd(null);
        return user;
    }

    @Override
    public Boolean updateUser(User user,HttpServletRequest req) {
        if(user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        long userUid = user.getUid();
        if(userUid <=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User currentUser = getCurrentUser(req);
        if(!isAdmin(currentUser)&&currentUser.getUid() != userUid){
            throw new BusinessException(ErrorCode.NOT_ADMIN);
        }
        int i = userMapper.updateById(user);
        if(userUid == currentUser.getUid()){
            req.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        }
        return i == 1;
    }

    @Override
    public Boolean isAdmin(User user) {
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"目标为空");
        }
        return Objects.equals(user.getRole(), UserConstant.ADMIN_ROLE);
    }

    @Override
    public Boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"未登录");
        }
        return Objects.equals(user.getRole(), UserConstant.ADMIN_ROLE);
    }
}

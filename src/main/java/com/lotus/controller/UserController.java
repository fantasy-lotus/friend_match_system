package com.lotus.controller;

import com.lotus.common.BaseResult;
import com.lotus.common.BusinessException;
import com.lotus.common.ErrorCode;
import com.lotus.common.ResultUtils;
import com.lotus.pojo.User;
import com.lotus.pojo.UserLoginRequest;
import com.lotus.pojo.UserRegisterRequest;
import com.lotus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;
    private static final String USER_LOGIN_STATE = "userLoginState";

    @PostMapping("/register")
    public BaseResult<Boolean> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String tel = userRegisterRequest.getTel();
        String pwd = userRegisterRequest.getPwd();
        String confirmPwd = userRegisterRequest.getConfirmPwd();
        if (StringUtils.isAnyBlank(tel, pwd, confirmPwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean res = userService.userRegister(tel, pwd, confirmPwd);
        return ResultUtils.success(res);
    }

    @PostMapping("/login")
    public BaseResult<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null || request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(request.getSession().getAttribute(USER_LOGIN_STATE) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请先退出登录");
        }
        String tel = userLoginRequest.getTel();
        String pwd = userLoginRequest.getPwd();
        if (StringUtils.isAnyBlank(tel, pwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User res = userService.userLogin(tel, pwd, request);
        return ResultUtils.success(res);
    }

    @PostMapping("/logout")
    public BaseResult<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(true);
    }
    @GetMapping("/currentUser")
    public BaseResult<User> getCurrentUser(HttpServletRequest request) {
        if(request==null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        User currentUser = userService.getCurrentUser(request);
//        Gson gson = new Gson();
//        Set<String> o = gson.fromJson(currentUser.getTags(), new TypeToken<Set<String>>() {
//        }.getType());
//        o.forEach(System.out::println);
        return ResultUtils.success(currentUser);
    }

    /**
     *
     * @param user - 前端不允许传密码字段
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResult<Boolean> userUpdate(@RequestBody User user, HttpServletRequest request) {
        if(user == null||request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if(user.getPwd()!=null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求非法");
        }
        Boolean b = userService.updateUser(user, request);
        return ResultUtils.success(b);
    }

    @GetMapping("/searchUsersByTags")
    public BaseResult<List<User>> getUsersByTags(@RequestParam List<String> tagsNameList) {
        if(tagsNameList==null|| tagsNameList.isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        List<User> tags = userService.getUsersByTags(tagsNameList);
        return ResultUtils.success(tags);
    }
}

package com.lotus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lotus.common.*;
import com.lotus.constant.UserConstant;
import com.lotus.pojo.User;
import com.lotus.pojo.UserLoginRequest;
import com.lotus.pojo.UserRegisterRequest;
import com.lotus.service.UserService;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spi.service.contexts.Defaults;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

//    @Autowired
//    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private Defaults defaults;

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
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请先退出登录");
        }
        String tel = userLoginRequest.getTel();
        String pwd = userLoginRequest.getPwd();
        if (StringUtils.isAnyBlank(tel, pwd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User res = userService.userLogin(tel, pwd, request);
        return ResultUtils.success(res);
    }

    @GetMapping("/logout")
    public BaseResult<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return ResultUtils.success(true);
    }

    @GetMapping("/current")
    public BaseResult<User> getCurrentUser(HttpServletRequest request) {
        if (request == null) {
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
     * @param user - 前端不允许传密码字段
     */
    @PostMapping("/update")
    public BaseResult<Boolean> userUpdate(@RequestBody User user, HttpServletRequest request) {
        if (user == null || request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (user.getPwd() != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求非法");
        }
        Boolean b = userService.updateUser(user, request);
        return ResultUtils.success(b);
    }

    @GetMapping("/search/byTags")
    public BaseResult<List<User>> getUsersByTags(@RequestParam List<String> tagsNameList) {
        if (tagsNameList == null || tagsNameList.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        List<User> tags = userService.getUsersByTags(tagsNameList);
        return ResultUtils.success(tags);
    }

    /**
     * 采用缓存的思想, 不过有点bug
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/recommend")
    public BaseResult<List<User>> recommendUsers(@RequestParam(name = "pageNum",defaultValue = "1") long pageNum,@RequestParam(name = "pageSize",defaultValue = "8") long pageSize, HttpServletRequest request) {
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if(pageNum <= 0 || pageSize <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不合法");
        }
        String key = String.format("lotus_match:user:recommend:%s", userService.getCurrentUser(request).getUid());
        RBucket<Object> bucket = redissonClient.getBucket(key);
        IPage<User> userPage = (IPage<User>)bucket.get();
        if( userPage != null){
            return ResultUtils.success(userPage.getRecords());
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("uid", userService.getCurrentUser(request).getUid());
        userPage = userService.page(new Page<>(pageNum,pageSize), queryWrapper);
        if (userPage == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        bucket.set(userPage,10,TimeUnit.SECONDS);
        return ResultUtils.success(userPage.getRecords());
    }

}

package org.fd.ase.grp15.ase_user_service.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboService;
import org.fd.ase.grp15.ase_user_service.entity.User;
import org.fd.ase.grp15.ase_user_service.exception.UserServiceException;
import org.fd.ase.grp15.ase_user_service.exception.code.UserServiceErrorCode;
import org.fd.ase.grp15.ase_user_service.repository.UserRepository;
import org.fd.ase.grp15.common.iservice.IUserService;
import org.fd.ase.grp15.common.iservice.user.dto.UserDTO;
import org.fd.ase.grp15.common.iservice.user.param.UserLoginParam;
import org.fd.ase.grp15.common.iservice.user.param.UserRegisterParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Service
@DubboService
@Validated
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${ase-user-service.admin-account}")
    private String ADMIN_ACCOUNT;

    @Value("${ase-user-service.admin-password}")
    private String ADMIN_PASSWORD;

    @Override
    @Validated
    public String register(@Valid UserRegisterParam.In in) {
        // 检查用户名重复
        if (userRepository.findUserByUsername(in.getUsername()) != null) {
            throw new UserServiceException(UserServiceErrorCode.ERR_USERNAME_EXISTED, "用户名已存在");
        }
        // 检查用户名是否包含密码
        if (in.getUsername().contains(in.getPassword())) {
            throw new UserServiceException(UserServiceErrorCode.ERR_PASSWORD_CONTAINS_USERNAME, "用户名不能包含密码");
        }
        // 映射参数
        User user = modelMapper.map(in, User.class);

        // 保存用户
        userRepository.save(user);

        // 调用sa-token登陆
        StpUtil.login(user.getUsername());

        return "注册成功";
    }

    @Override
    @Validated
    public String login(@Valid UserLoginParam.In in) {
        // 给管理员留一个后门
        if (Objects.equals(in.getUsername(), ADMIN_ACCOUNT) && Objects.equals(in.getPassword(), ADMIN_PASSWORD)) {
            StpUtil.login(in.getUsername());
            return "登录成功";
        }

        User user = userRepository.findUserByUsername(in.getUsername());
        if (user != null && Objects.equals(user.getPassword(), in.getPassword())) {
            // 调用sa-token登陆
            StpUtil.login(user.getUsername());
            return "登录成功";
        }
        throw new UserServiceException(UserServiceErrorCode.ERR_BAD_REQUEST, "用户名或密码错误");
    }

    @Override
    public String logout() {
        // 调用sa-token注销
        StpUtil.logout();

        return "注销成功";
    }

    @Override
    public UserDTO userInfoByUsername(String username) {
        StpUtil.checkLogin();
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UserServiceException(UserServiceErrorCode.ERR_USER_NOT_FOUND, "用户不存在");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> userInfoByRealName(String realName) {
        StpUtil.checkLogin();
        List<User> users = userRepository.findUsersByRealName(realName);
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }
}

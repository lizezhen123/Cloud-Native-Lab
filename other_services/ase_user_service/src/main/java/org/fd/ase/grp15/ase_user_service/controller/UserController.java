package org.fd.ase.grp15.ase_user_service.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.fd.ase.grp15.ase_user_service.service.impl.UserConferenceRoleServiceImpl;
import org.fd.ase.grp15.ase_user_service.service.impl.UserServiceImpl;
import org.fd.ase.grp15.ase_user_service.web.bind.response.WithDataSuccessfulResponse;
import org.fd.ase.grp15.common.enums.ConferenceRole;
import org.fd.ase.grp15.common.iservice.user.dto.UserConferenceRoleDTO;
import org.fd.ase.grp15.common.iservice.user.dto.UserDTO;
import org.fd.ase.grp15.common.iservice.user.param.UserLoginParam;
import org.fd.ase.grp15.common.iservice.user.param.UserRegisterParam;
import org.fd.ase.grp15.ase_user_service.web.bind.response.NoDataSuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserConferenceRoleServiceImpl userConferenceRoleService;

    @PostMapping("/register")
    public NoDataSuccessfulResponse register(@RequestBody @Validated UserRegisterParam.In in) {
        String msg = userService.register(in);
        return new NoDataSuccessfulResponse(msg);
    }

    @PostMapping("/login")
    public NoDataSuccessfulResponse login(@RequestBody @Validated UserLoginParam.In in) {
        String msg = userService.login(in);
        return new NoDataSuccessfulResponse(msg);
    }

    @PostMapping("/logout")
    public NoDataSuccessfulResponse logout() {
        String msg = userService.logout();
        return new NoDataSuccessfulResponse(msg);
    }

    @GetMapping("/userInfo/self")
    public WithDataSuccessfulResponse<UserDTO> userInfoSelf() {
        StpUtil.checkLogin();
        String username = (String) StpUtil.getLoginId();
        UserDTO userDTO = userService.userInfoByUsername(username);
        return new WithDataSuccessfulResponse<>("获取用户信息成功", userDTO);
    }

    @GetMapping("/userInfo/byUsername/{username}")
    public WithDataSuccessfulResponse<UserDTO> userInfoByUsername(@PathVariable String username) {
        StpUtil.checkLogin();
        UserDTO userDTO = userService.userInfoByUsername(username);
        return new WithDataSuccessfulResponse<>("获取用户信息成功", userDTO);
    }

    @GetMapping("/userInfo/byRealName/{realName}")
    public WithDataSuccessfulResponse<List<UserDTO>> userInfoByRealName(@PathVariable String realName) {
        StpUtil.checkLogin();
        List<UserDTO> userDTOS = userService.userInfoByRealName(realName);
        return new WithDataSuccessfulResponse<>("获取用户信息成功", userDTOS);
    }

    @GetMapping("/userConferenceRole/self/{conferenceName}")
    public WithDataSuccessfulResponse<List<UserConferenceRoleDTO>> userConferenceRoleSelf(@PathVariable String conferenceName) {
        StpUtil.checkLogin();
        String username = (String) StpUtil.getLoginId();

        List<ConferenceRole> roles = userConferenceRoleService.loadUserRolesInConference(username, conferenceName);
        List<UserConferenceRoleDTO> userConferenceRoleDTOS = roles.stream().map(role -> new UserConferenceRoleDTO(username, conferenceName, role)).toList();

        return new WithDataSuccessfulResponse<>("获取用户会议角色成功", userConferenceRoleDTOS);
    }


}

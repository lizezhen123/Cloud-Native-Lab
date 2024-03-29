package org.fd.ase.grp15.ase_user_service.service.impl;

import jakarta.validation.ConstraintViolationException;
import org.fd.ase.grp15.ase_user_service.exception.UserServiceException;
import org.fd.ase.grp15.ase_user_service.exception.code.UserServiceErrorCode;
import org.fd.ase.grp15.ase_user_service.repository.UserRepository;
import org.fd.ase.grp15.common.iservice.user.dto.UserDTO;
import org.fd.ase.grp15.common.iservice.user.param.UserLoginParam;
import org.fd.ase.grp15.common.iservice.user.param.UserRegisterParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    void prepare() {
        userRepository.deleteAll();
    }

    @Test
    public void testRegister() {
        prepare();

        // normal register
        UserRegisterParam.In in = new UserRegisterParam.In();
        in.setUsername("huajuan");
        in.setPassword("huajuan123");
        in.setEmail("zhj630985214@gmail.com");
        in.setInstitutionName("FDU");
        in.setArea("Shanghai");
        in.setRealName("Hua Juan");

        userService.register(in);

        // 参数错误
        in.setUsername("ha"); // 用户名长度不足
        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.register(in));

        // 重复注册
        in.setUsername("huajuan");
        UserServiceException ex = Assertions.assertThrows(UserServiceException.class, () -> userService.register(in));
        Assertions.assertEquals(UserServiceErrorCode.ERR_USERNAME_EXISTED, ex.getCode());
    }

    @Test
    public void testLogin() {
        prepare();

        // 注册一个账号
        UserRegisterParam.In in = new UserRegisterParam.In();
        in.setUsername("huajuan");
        in.setPassword("huajuan123");
        in.setEmail("zhj630985214@gmail.com");
        in.setInstitutionName("FDU");
        in.setArea("Shanghai");
        in.setRealName("Hua Juan");

        userService.register(in);


        // 正常登录
        UserLoginParam.In in2 = new UserLoginParam.In();
        in2.setUsername("huajuan");
        in2.setPassword("huajuan123");
        userService.login(in2);

        // 密码错误
        in2.setPassword("huajuan1234");
        UserServiceException ex = Assertions.assertThrows(UserServiceException.class, () -> userService.login(in2));
        Assertions.assertEquals(UserServiceErrorCode.ERR_BAD_REQUEST, ex.getCode());
    }


    @Test
    public void testGetUserInfoByUsername() {
        prepare();

        // 注册一个账号
        UserRegisterParam.In in = new UserRegisterParam.In();
        in.setUsername("huajuan");
        in.setPassword("huajuan123");
        in.setEmail("zff@173.com");
        in.setInstitutionName("FDU");
        in.setArea("Shanghai");
        in.setRealName("Hua Juan");

        userService.register(in);

        // 正常获取
        Assertions.assertNotNull(userService.userInfoByUsername("huajuan"));

        // 不存在的用户
        UserServiceException ex = Assertions.assertThrows(UserServiceException.class, () -> userService.userInfoByUsername("huajuan2"));
        Assertions.assertEquals(UserServiceErrorCode.ERR_USER_NOT_FOUND, ex.getCode());
    }

    @Test
    public void testGetUserInfoByRealName() {
        prepare();

        // 注册一个账号
        UserRegisterParam.In in = new UserRegisterParam.In();
        in.setUsername("huajuan");
        in.setPassword("huajuan123");
        in.setEmail("zhj@162.com");
        in.setInstitutionName("FDU");
        in.setArea("Shanghai");
        in.setRealName("Hua Juan");

        userService.register(in);

        // 正常获取
        List<UserDTO> info = userService.userInfoByRealName("Hua Juan");
        Assertions.assertEquals(1, info.size());

        // 不存在的用户
        Assertions.assertEquals(0, userService.userInfoByRealName("Hua Juan2").size());
    }
}

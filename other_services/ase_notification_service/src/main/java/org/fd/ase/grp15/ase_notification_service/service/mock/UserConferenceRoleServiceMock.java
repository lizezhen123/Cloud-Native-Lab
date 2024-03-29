package org.fd.ase.grp15.ase_notification_service.service.mock;

import org.fd.ase.grp15.common.enums.ConferenceRole;
import org.fd.ase.grp15.common.iservice.IUserConferenceRoleService;
import org.fd.ase.grp15.common.iservice.user.dto.UserConferenceRoleDTO;

import java.util.ArrayList;
import java.util.List;

public class UserConferenceRoleServiceMock implements IUserConferenceRoleService {
    @Override
    public List<ConferenceRole> loadUserRolesInConference(String username, String conferenceName) {
        return new ArrayList<>();
    }

    @Override
    public List<UserConferenceRoleDTO> loadUserRolesInConference(String username) {
        return new ArrayList<>();
    }

    @Override
    public String addRoleToUserInConference(String username, String conferenceName, ConferenceRole role) {
        throw new RuntimeException("未找到可用的IUserConferenceRoleService服务");
    }

    @Override
    public String removeRoleFromUserInConference(String username, String conferenceName, ConferenceRole role) {
        throw new RuntimeException("未找到可用的IUserConferenceRoleService服务");
    }

    @Override
    public Boolean checkRoleOfUserInConference(String username, String conferenceName, ConferenceRole role) {
        throw new RuntimeException("未找到可用的IUserConferenceRoleService服务");
    }
}

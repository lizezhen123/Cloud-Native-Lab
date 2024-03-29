package org.fd.ase.grp15.ase_user_service.config;

import org.fd.ase.grp15.ase_user_service.entity.UserConferenceRole;
import org.fd.ase.grp15.common.enums.ConferenceRole;
import org.fd.ase.grp15.common.iservice.user.dto.UserConferenceRoleDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    protected ModelMapper userModelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        TypeMap<UserConferenceRole, UserConferenceRoleDTO> typeMap = modelMapper.createTypeMap(UserConferenceRole.class, UserConferenceRoleDTO.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getId().getConferenceName(), UserConferenceRoleDTO::setConferenceName));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getId().getUsername(), UserConferenceRoleDTO::setUsername));
        typeMap.addMappings(mapper -> mapper.map(src -> {
            try {
                return ConferenceRole.valueOf(src.getId().getRole());
            } catch (Exception ex) {
                return null;
            }
        }, UserConferenceRoleDTO::setRole));

        return modelMapper;
    }
}

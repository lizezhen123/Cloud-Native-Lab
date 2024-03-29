package org.fd.ase.grp15.ase_conference_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    protected ModelMapper conferenceModelMapper() {
        return new ModelMapper();
    }
}

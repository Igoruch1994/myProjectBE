package project.mapper;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.dto.RegistrationDTO;
import project.entity.User;

@Component
public class RegistrartionDTOToUserMapper extends CustomMapper<RegistrationDTO, User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void mapAtoB(final RegistrationDTO a, final User b, final MappingContext context) {
        super.mapAtoB(a, b, context);
        b.setPassword(passwordEncoder.encode(a.getPassword()));
    }

    @Override
    public void mapBtoA(final User b, final RegistrationDTO a, final MappingContext context) {
        super.mapBtoA(b, a, context);
    }

}

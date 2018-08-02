package project.mapper;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import project.dto.RegistrationDTO;
import project.entity.User;

public class RegistrartionDTOToUserMapper extends CustomMapper<RegistrationDTO, User> {

    @Override
    public void mapAtoB(final RegistrationDTO a, final User b, final MappingContext context) {
        super.mapAtoB(a, b, context);
    }

    @Override
    public void mapBtoA(final User b, final RegistrationDTO a, final MappingContext context) {
        super.mapBtoA(b, a, context);
    }

}

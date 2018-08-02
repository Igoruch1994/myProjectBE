package project.mapper;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import project.dto.UserDTO;
import project.entity.User;

@Component
public class UserToUserDTOMapper extends CustomMapper<User, UserDTO> {

    @Override
    public void mapAtoB(final User a, final UserDTO b, final MappingContext context) {
        super.mapAtoB(a, b, context);
    }

}

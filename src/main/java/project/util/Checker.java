package project.util;

import project.dto.LoginDTO;

public class Checker {

    public static boolean checkParamsOnNull(LoginDTO dto) {
        return checkParamsOnNull(dto.getEmail(), dto.getPassword());
    }

    public static boolean checkParamsOnNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return false;
            }

            if (obj instanceof String)
                if (((String) obj).isEmpty())
                    return false;
        }
        return true;
    }

}

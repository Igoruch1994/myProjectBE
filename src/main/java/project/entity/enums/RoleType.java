package project.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Strings;

public enum RoleType {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String paramName;

    RoleType(String paramName) {
        this.paramName = paramName;
    }

    @JsonCreator
    public static RoleType getRoleName(final String paramName) {
        if (Strings.isNullOrEmpty(paramName)) {
            return null;
        }
        switch (paramName.toLowerCase()) {
            case "role_admin":
                return ROLE_ADMIN;
            case "role_user":
                return ROLE_USER;
        }
        return null;
    }

    public String getRoleName() {
        return paramName;
    }

}

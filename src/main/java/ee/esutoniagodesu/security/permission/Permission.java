package ee.esutoniagodesu.security.permission;

import ee.esutoniagodesu.domain.library.table.Reading;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.util.JCString;

public enum Permission {

    reading_read((object) -> {
        Reading reading = (Reading) object;
        return reading.isShared() || reading.isCreatedBy(SecurityUtils.getUserUuid());
    }),

    //kõikil on lubatud artiklit luua
    reading_create((object) -> true),

    //ainult enda oma on lubatud uuendada
    reading_update((object) -> {
        SecurityUtils.isUserInRole(AuthoritiesConstants.SENSEI);
        Reading reading = (Reading) object;
        return reading.isCreatedBy(SecurityUtils.getUserUuid());
    }),

    reading_delete((object) -> {
        Reading reading = (Reading) object;
        return reading.isCreatedBy(SecurityUtils.getUserUuid());
    });

    private FunctionalPermission functional;

    Permission(FunctionalPermission functional) {
        this.functional = functional;
    }

    public static Permission valueOf(Object permission) {
        return JCString.stringToEnum(permission.toString(), Permission.class);
    }

    public boolean hasPermission(Object targetDomainObject) {
        return functional.hasPermission(targetDomainObject);
    }
}

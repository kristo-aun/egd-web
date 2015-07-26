package ee.esutoniagodesu.security.permission;

import ee.esutoniagodesu.domain.core.table.TofuSentenceTranslation;
import ee.esutoniagodesu.domain.library.table.Reading;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.util.JCString;

import java.util.Properties;

public enum Permission {

    reading_read((object) -> {
        Reading reading = (Reading) object;
        return reading.isShared() || reading.isCreatedBy(SecurityUtils.getUserUuid());
    }),

    //kõikil on lubatud artiklit luua
    reading_create((object) -> true),

    //ainult enda oma on lubatud uuendada
    reading_update((object) -> {
        Reading reading = (Reading) object;
        return reading.isCreatedBy(SecurityUtils.getUserUuid());
    }),

    reading_delete((object) -> {
        Reading reading = (Reading) object;
        return reading.isCreatedBy(SecurityUtils.getUserUuid());
    }),

    shafile_read((object) -> {
        Properties properties = (Properties) object;
        String rolestring = properties.getProperty("roles-allowed");
        if (rolestring != null && rolestring.length() > 0) {
            String[] roles = rolestring.split(",");
            for (String role : roles) {
                if (SecurityUtils.isUserInRole(role)) return true;
            }
            return false;
        }
        return true;
    }),

    tofu_translation_save((object) -> {
        TofuSentenceTranslation entity = (TofuSentenceTranslation) object;
        if (entity.getId() != null && entity.getId() > 0) {
            return entity.getCreatedBy().equals(SecurityUtils.getUserUuid());
        }
        return true;
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

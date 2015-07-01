package ee.esutoniagodesu.security.permission;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    @Inject
    private ProjectDAO dao;

    private static boolean hasRoleAdmin() {
        return SecurityUtils.isUserInRole(AuthoritiesConstants.ADMIN);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.debug("hasPermission: {}", permission);
        //admin võib kõike teha
        boolean result = hasRoleAdmin() || Permission.valueOf(permission).hasPermission(targetDomainObject);
        log.debug("hasPermission: result={}", result);
        return result;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Object entity;
        try {
            entity = dao.find(Class.forName(targetType), targetId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return hasPermission(authentication, entity, permission);
    }
}

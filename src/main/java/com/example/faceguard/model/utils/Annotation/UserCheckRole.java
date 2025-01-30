package com.example.faceguard.model.utils.Annotation;

import com.example.faceguard.model.Users;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserCheckRole {

    @Before(value = "@annotation(roleCheckName)")

    public String permissionCheck(RoleCheckName roleCheckName) throws RuntimeException {
        Users users = (Users)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean is=false;
        for (GrantedAuthority authority : users.getAuthorities()) {
            if (authority.getAuthority().equals(roleCheckName.value())){
                is=true;
                break;
            }
        }
        if (!is) {
            throw new com.example.faceguard.exceptions.RuntimeException("403","Role not found");
        }
        return "aa";
    }
}

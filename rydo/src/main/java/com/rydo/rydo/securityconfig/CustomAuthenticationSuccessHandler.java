package com.rydo.rydo.securityconfig;

import java.io.IOException;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        String redirectUrl = "/login?error=true"; // Default to login if something goes wrong
        
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_USER")) {
                redirectUrl = "/user/home";
                break;
            } else if (role.equals("ROLE_DRIVER")) {
                redirectUrl = "/driver/home";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}

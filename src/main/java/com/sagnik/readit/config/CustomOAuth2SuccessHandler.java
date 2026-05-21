//package com.sagnik.readit.config;
//
//import com.sagnik.readit.service.JwtService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
//    private final JwtService jwtService;
//
//    @Value("${security.jwt.expiration-time}")
//    private long EXPIRATION_TIME;
//
//    public CustomOAuth2SuccessHandler(JwtService jwtService) {
//        this.jwtService = jwtService;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        OAuth2User user = (OAuth2User) authentication.getPrincipal();
//        String username = user.getAttribute("login");
//        String token = jwtService.generateToken(username);
//        Cookie jwtCookie = new Cookie("jwt", token);
//
//        jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(true);
//        jwtCookie.setPath("/");
//        jwtCookie.setMaxAge((int) (300000 / 1000));
//        response.addCookie(jwtCookie);
//        response.setContentType("application/json");
//        response.getWriter().write("""
//                {
//                    "message": "Login successful"
//                }
//                """);
//        response.sendRedirect("/");
//    }
//}

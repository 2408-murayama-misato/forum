package com.example.ToYokoNa.filter;


import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class loginFilter implements Filter {

    @Autowired
    HttpSession session;

    @Autowired
    LoginSession loginSession;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        session = httpRequest.getSession();
        if (session != null && session.getAttribute("loginUser") != null) {
            if (!loginSession.alreadyLogin(String.valueOf(request))) {
                chain.doFilter(httpRequest, httpResponse);
            } else {
                String errorMessage = "既に他の端末でログインしています。";
                session.setAttribute("errorMessage", errorMessage);
                httpResponse.sendRedirect("/ToYokoNa/userLogin");
            }
        } else {
            String errorMessage = "ログインしてください";
            session.setAttribute("errorMessage", errorMessage);
            httpResponse.sendRedirect("/ToYokoNa/userLogin");
        }
    }
}

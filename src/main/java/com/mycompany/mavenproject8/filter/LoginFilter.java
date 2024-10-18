/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject8.filter;

import com.mycompany.mavenproject8.entity.UserManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *Hier filtern wir in http. Wir nehmen den Wert von user.catagory und bestimmen, auf welche Seiten 
 * dieser Benutzer auf dieser Seite zugreifen kann oder nicht. Wir filtern, indem wir den 
 * erstellten validUser(session)-Wert lesen.
        user.catagory = 3 ( Admin )
        user.catagory = 2 ( Benutzer )
 * @author erkan
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;

        String url = request.getRequestURI();

        HttpSession session = request.getSession();

        UserManager user = null;

        var i = 0;

        user = session != null ? (UserManager) session.getAttribute("validUser") : null;

        if (user == null) {
            if (url.contains("LogoutApp") || url.contains("UserReportLost")
                  || url.contains("AdminControlBergPerson")
                  || url.contains("AdminControlLost")
                  || url.contains("AdminControlPanel")
                  || url.contains("AdminControlUser")
                  || url.contains("AdminApp")) {
                response.sendRedirect(request.getContextPath() + "/pages/LoginApp.xhtml");
            } else {
                fc.doFilter(sr, sr1);
            }
        } else {
            //admin
            if (user.getKundenkatagori() == 3) {
                if (url.contains("UserRegisterApp") || url.contains("UserReportLost")
                   || url.contains("AnonymMeldung")
                   || url.contains("index")
                   || url.contains("LoginApp")) {
                    response.sendRedirect(request.getContextPath() + "/pages/AdminControlPanel.xhtml");
                } else if (url.contains("LogoutApp" )) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/pages/LoginApp.xhtml");
                }
                {
                    fc.doFilter(sr, sr1);
                }
            }
            //user
            if (user.getKundenkatagori() == 2) {
                if (url.contains("RegisterApp")|| url.contains("AdminApp")
                    || url.contains("AdminControlBergPerson")
                    || url.contains("AdminControlLost")
                    || url.contains("AdminControlPanel")
                    || url.contains("AdminControlUser")   
                    || url.contains("AnonymMeldung")
                    || url.contains("index")) {
                    response.sendRedirect(request.getContextPath() + "/pages/UserReportLost.xhtml");
                } else if (url.contains("LogoutApp")) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/pages/LoginApp.xhtml");
                }
                {
                    fc.doFilter(sr, sr1);
                }
            }
        }

    }

}

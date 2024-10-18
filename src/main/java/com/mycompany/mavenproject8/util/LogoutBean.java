/**
 *  Logout werden von dieser Seite aus durchgeführt
 * @author erkan
 */
package com.mycompany.mavenproject8.util;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Named("logoutyap")
@RequestScoped
public class LogoutBean implements HttpSessionListener{
    private static final Set<HttpSessionEvent> activeSessions = 
        Collections.synchronizedSet(new HashSet<>());

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();

        
        sessionMap.remove("applogincontroller"); 
        sessionMap.remove("koordinatendatenbean");
        sessionMap.remove("userdatenbean");
        sessionMap.remove("SAUKoordinatDatens");
        sessionMap.remove("SAUUserDatens");
        
        sessionMap.clear();
        invalidateAllSessions();
        
        return "logout?faces-redirect=true";
    }
public static void invalidateAllSessions() {
        synchronized (activeSessions) {
            for (HttpSessionEvent sessionEvent : activeSessions) {
                sessionEvent.getSession().invalidate();
            }
            activeSessions.clear();
        }
    }
}

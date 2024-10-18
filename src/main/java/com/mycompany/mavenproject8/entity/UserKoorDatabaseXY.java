package com.mycompany.mavenproject8.entity;

/**
 *
 * @author erkan
 */
public class UserKoorDatabaseXY {
    private double kundenx;
        private double kundeny;
        private String username;
        private boolean situation;

    public UserKoorDatabaseXY(double kundenx, double kundeny, String username, boolean situation) {
        this.kundenx = kundenx;
        this.kundeny = kundeny;
        this.username = username;
        this.situation = situation;
    }

    public double getKundenx() {
        return kundenx;
    }

    public void setKundenx(double kundenx) {
        this.kundenx = kundenx;
    }

    public double getKundeny() {
        return kundeny;
    }

    public void setKundeny(double kundeny) {
        this.kundeny = kundeny;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSituation() {
        return situation;
    }

    public void setSituation(boolean situation) {
        this.situation = situation;
    }
        
}

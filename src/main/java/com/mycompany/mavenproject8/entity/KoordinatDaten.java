/**
 *
 * @author erkan
 */
package com.mycompany.mavenproject8.entity;

import java.util.Date;


public class KoordinatDaten {

    private int id;
    private double kundenx;
    private double kundeny;
    private boolean situation;
    private String bergungsperson;
    private String created_at;
    private int kundenkatagori;
    private String username;
    private String reportednote="";
    private int optiondata;  //
    private float geschatzteGrosse;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isSituation() {
        if(this.optiondata<3 && this.optiondata >0 ){
        situation = false;
            }else{
        situation = true;
                }
        return situation;
    }

    public void setSituation(boolean situation) {
        this.situation = situation;
    }

    public String getBergungsperson() {
        return bergungsperson;
    }

    public void setBergungsperson(String bergungsperson) {
        this.bergungsperson = bergungsperson;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getKundenkatagori() {
        return kundenkatagori;
    }

    public void setKundenkatagori(int kundenkatagori) {
        this.kundenkatagori = kundenkatagori;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReportednote() {
        return reportednote;
    }

    public void setReportednote(String reportednote) {
        this.reportednote = reportednote;
    }

    public int getOptiondata() {
        return optiondata;
    }

    public void setOptiondata(int optiondata) {
        this.optiondata = optiondata;
    }
    
    
    public void setOptiondataTo1() {
        this.optiondata = 1;
    }

    public void setOptiondataTo2() {
        this.optiondata = 2;
    }

    public void setOptiondataTo3() {
        this.optiondata = 3;
    }

    public void setOptiondataTo4() {
        this.optiondata = 4;
    }

    public float getGeschatzteGrosse() {
        return geschatzteGrosse;
    }

    public void setGeschatzteGrosse(float geschatzteGrosse) {
        this.geschatzteGrosse = geschatzteGrosse;
    }

    @Override
    public String toString() {
        return "KoordinatDaten{" + "id=" + id + ", kundenx=" + kundenx + ", kundeny=" + kundeny + ", situation=" + situation + ", bergungsperson=" + bergungsperson + ", created_at =" + created_at + ", kundenkatagori=" + kundenkatagori + ", username=" + username + ", reportednote=" + reportednote + ", optiondata=" + optiondata + ", geschatzteGrosse=" + geschatzteGrosse + '}';
    }

    

    

    
}

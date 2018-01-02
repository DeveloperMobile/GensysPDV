package com.codigosandroid.gensyspdv.cloud;

/**
 * Created by Tiago on 02/01/2018.
 */

public class Cloud {

    private String loginEmail;
    private String mysqlDb;
    private String mysqlUser;
    private String mysqlPass;
    private String hostWeb;

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getMysqlDb() {
        return mysqlDb;
    }

    public void setMysqlDb(String mysqlDb) {
        this.mysqlDb = mysqlDb;
    }

    public String getMysqlUser() {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser) {
        this.mysqlUser = mysqlUser;
    }

    public String getMysqlPass() {
        return mysqlPass;
    }

    public void setMysqlPass(String mysqlPass) {
        this.mysqlPass = mysqlPass;
    }

    public String getHostWeb() {
        return hostWeb;
    }

    public void setHostWeb(String hostWeb) {
        this.hostWeb = hostWeb;
    }

}

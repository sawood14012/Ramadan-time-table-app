package com.example.sawood.cardvie;

public class UserProfile {
    public String userno;
    public String userEmail;
    public String userName;

    public UserProfile(){
    }

    public UserProfile(String userAge, String userEmail, String userName) {
        this.userno = userAge;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userAge) {
        this.userno = userno;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}


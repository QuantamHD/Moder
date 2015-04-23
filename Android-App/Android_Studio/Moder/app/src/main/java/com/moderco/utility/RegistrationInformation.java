package com.moderco.utility;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ethan on 4/22/2015.
 */
@SuppressWarnings("serial")
public class RegistrationInformation implements Serializable{
    private String email;
    private String password;
    private String gender;
    private int age;

    public RegistrationInformation(String email, String password, String gender, int age) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }

    public RegistrationInformation() {
        email = "";
        password = "";
        gender = "";
        age = -1;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if(gender.equals("Select Your Gender")){
            this.gender = "";
            return;
        }
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegistrationInformation{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}

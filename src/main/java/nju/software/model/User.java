package nju.software.model;

import java.io.Serializable;

public class User implements Serializable {
    private String nickname;
    private String name;
    private String password;
    private char gender;
    private String apartment;
    private String job;
    private String contact;
    private String email;
    private String head_img;

    public User(String nickname,String name,String password,char gender,String apartment,String job,String contact,String email,String head_img){
        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.apartment = apartment;
        this.job = job;
        this.contact = contact;
        this.email = email;
        this.head_img = head_img;
    }

    public User(String nickname,char gender,String apartment,String job,String contact,String email){
        this.nickname = nickname;
        this.gender = gender;
        this.apartment = apartment;
        this.job = job;
        this.contact = contact;
        this.email = email;
    }

    public User(){

    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", apartment='" + apartment + '\'' +
                ", job='" + job + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", head_img='" + head_img + '\'' +
                '}';
    }
}

package com.example.hazardcrowdsourcing.Model;

public class Member {

    String name;
    String sid;
    String group;
    String code;
    String fakulti;
    Integer imageUrl;

    public Member(String name, String sid , String group , String code, String fakulti  , Integer imageUrl) {
        this.name = name;
        this.sid = sid;
        this.group = group;
        this.code = code;
        this.fakulti = fakulti;
        this.imageUrl = imageUrl;
    }

    public String getFakulti() {
        return fakulti;
    }

    public void setFakulti(String fakulti) {
        this.fakulti = fakulti;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}

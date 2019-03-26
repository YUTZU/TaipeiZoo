package com.example.yutzu.taipeizoo.Tool;

import java.io.Serializable;

public class Plant implements Serializable {

    String name_ch, name_en, name_latin, location, picUrl, nickName, feature, family, function,brief;
    public Plant(String name_ch, String name_en,String name_latin, String picUrl, String nickName, String feature, String family, String function,String brief){
        this.name_ch=name_ch;
        this.name_en=name_en;
        this.name_latin=name_latin;
//        this.location=location;
        this.picUrl=picUrl;
        this.nickName=nickName;
        this.feature=feature;
        this.family=family;
        this.function=function;
        this.brief=brief;
    }

    public String getName() {
        return name_ch;
    }

    public void setName_ch(String name_ch) {
        this.name_ch = name_ch;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_latin() {
        return name_latin;
    }

    public void setName_latin(String name_latin) {
        this.name_latin = name_latin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFunction() {
        return function;
    }

    public String getName_ch() {
        return name_ch;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}

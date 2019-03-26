package com.example.yutzu.taipeizoo.Tool;

import com.example.yutzu.taipeizoo.Tool.Plant;

import java.io.Serializable;
import java.util.List;

public class Zone implements Serializable{

    public String url, info, category, name, meno, picurl;
    public int id, no;
    public List<Plant> plants;

    public Zone(String picurl, String info, int no, String category, String name, String meno, int id, String url){
        super();
        this.picurl=picurl;
        this.info=info;
        this.no=no;
        this.category=category;
        this.name=name;
        this.meno=meno;
        this.id=id;
        this.url=url;
    }
    public Zone(String name, String picurl){
        this.picurl= picurl;
        this.name= name;
    }

    public List<Plant> getPlants() {
        return plants;
    }
    public void setPlants(List<Plant> plants){
        this.plants=plants;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}

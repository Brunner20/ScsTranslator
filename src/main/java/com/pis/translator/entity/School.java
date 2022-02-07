package com.pis.translator.entity;

public class School {

    String id;
    String name;
    String nameEng;
    String address;
    String searchArea;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng.trim();
    }

    public String getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(String searchArea) {
        this.searchArea = searchArea.trim();
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        String scs = String.format("%s <-concept_school;%n=> nrel_main_idtf:%n", id);
        if (nameEng != null) {
            scs = scs + String.format("[%s]%n(* <- lang_en;; <- name_en;;*);%n", nameEng);
        }
        scs = scs + String.format("[%s]%n(* <-lang_ru;;<- name_ru;;<- name;;*);%n", name);
        scs = scs + String.format("=>nrel_search_area: %s;;", searchArea);
        return scs;
    }


}

package com.pluralsight.model;

public class Speaker {
    private Long id;
    private String name;
    private String company;


    public Speaker(){
        this.name = null;
        this.company = null;
    }
    public Speaker(String name, String company){
        this.name = name;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

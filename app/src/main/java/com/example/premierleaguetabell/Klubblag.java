package com.example.premierleaguetabell;

//KÄLLA: https://hv.instructure.com/courses/4287/pages/vg-forelasningar-man-26-apr?module_item_id=134666

public class Klubblag {
    private int id;
    private String Klubbnamn;
    private String Klubbstad;
    private Integer Klubvinst;
    private Integer KlubbOavgjort;
    private Integer KlubbForlust;
    public Integer Totalpoang;

    public Klubblag() {
    }

    public Klubblag(int id, String klubbnamn, String klubbstad, Integer klubvinst, Integer klubbOavgjort, Integer klubbForlust, Integer totalpoang) {
        this.id = id;
        Klubbnamn = klubbnamn;
        Klubbstad = klubbstad;
        Klubvinst = klubvinst;
        KlubbOavgjort = klubbOavgjort;
        KlubbForlust = klubbForlust;
        Totalpoang = totalpoang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getKlubbnamn() {

        return Klubbnamn;
    }

    public void setKlubbnamn(String klubbnamn) {

        Klubbnamn = klubbnamn;
    }

    public String getKlubbstad() {

        return Klubbstad;
    }

    public void setKlubbstad(String klubbstad) {


        Klubbstad = klubbstad;
    }

    public Integer getKlubvinst()
    {
        return Klubvinst;
    }

    public void setKlubvinst(Integer klubvinst) {

        Klubvinst = klubvinst;
    }

    public Integer getKlubbOavgjort() {
        return
                KlubbOavgjort;
    }

    public void setKlubbOavgjort(Integer klubbOavgjort) {

        KlubbOavgjort = klubbOavgjort;
    }

    public Integer getKlubbForlust() {
        return
                KlubbForlust;
    }

    public void setKlubbForlust(Integer klubbForlust) {

        KlubbForlust = klubbForlust;
    }

    public Integer getTotalpoang() {

        return Totalpoang;
    }

    public  void setTotalpoang(Integer totalpoang) {

        Totalpoang = totalpoang;
    }

}

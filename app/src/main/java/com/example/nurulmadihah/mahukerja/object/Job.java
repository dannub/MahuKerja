package com.example.nurulmadihah.mahukerja.object;

public class Job {
    private String id;
    private  String title;
    private  String skill;
    private  String payment;
    private  String contacperson;
    private  String deskripsi;
    private  String pemasang;
    private  String status;
    private String image;

    public Job() {}
    public Job(String id, String title, String skill, String payment, String contacperson, String deskripsi, String pemasang, String status, String image) {
        this.id = id;
        this.title = title;
        this.skill = skill;
        this.payment = payment;
        this.contacperson = contacperson;
        this.deskripsi = deskripsi;
        this.pemasang = pemasang;
        this.status = status;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getContacperson() {
        return contacperson;
    }

    public void setContacperson(String contacperson) {
        this.contacperson = contacperson;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPemasang() {
        return pemasang;
    }

    public void setPemasang(String pemasang) {
        this.pemasang = pemasang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package com.example.nurulmadihah.mahukerja.object;

public class Tawaran {
    String id, id_user, id_job, job_user;
    int tawaran, status;
    public Tawaran(){}
    public Tawaran(String id, String id_user, String id_job, String job_user, int tawaran, int status) {
        this.id = id;
        this.id_user = id_user;
        this.id_job = id_job;
        this.job_user = job_user;
        this.tawaran = tawaran;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_job() {
        return id_job;
    }

    public void setId_job(String id_job) {
        this.id_job = id_job;
    }

    public String getJob_user() {
        return job_user;
    }

    public void setJob_user(String job_user) {
        this.job_user = job_user;
    }

    public int getTawaran() {
        return tawaran;
    }

    public void setTawaran(int tawaran) {
        this.tawaran = tawaran;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

package com.stylefeng.guns.modular.support.model;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class file_yu  implements Serializable {
    private MultipartFile file;

    private String matname;

    private String mattypecode;

    private int matcount;

    private int matpage;

    private String matkindcode;

    private int imagedescid;

    private int imageid;

    private byte[] imagefile;

    public byte[] getImagefile() {
        return imagefile;
    }

    public void setImagefile(byte[] imagefile) {
        this.imagefile = imagefile;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getMatname() {
        return matname;
    }

    public void setMatname(String matname) {
        this.matname = matname;
    }

    public String getMattypecode() {
        return mattypecode;
    }

    public void setMattypecode(String mattypecode) {
        this.mattypecode = mattypecode;
    }

    public int getMatcount() {
        return matcount;
    }

    public void setMatcount(int matcount) {
        this.matcount = matcount;
    }

    public int getMatpage() {
        return matpage;
    }

    public void setMatpage(int matpage) {
        this.matpage = matpage;
    }

    public String getMatkindcode() {
        return matkindcode;
    }

    public void setMatkindcode(String matkindcode) {
        this.matkindcode = matkindcode;
    }

    public int getImagedescid() {
        return imagedescid;
    }

    public void setImagedescid(int imagedescid) {
        this.imagedescid = imagedescid;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }



}

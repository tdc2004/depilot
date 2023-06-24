package com.chinhtd.quanlyxe1;

import java.io.Serializable;

public class xe implements Serializable {
    String ten, HangSx;
    int NamSX;
    double giaBan;

    public xe(String ten, String hangSx, int namSX, double giaBan) {
        this.ten = ten;
        HangSx = hangSx;
        NamSX = namSX;
        this.giaBan = giaBan;
    }
}

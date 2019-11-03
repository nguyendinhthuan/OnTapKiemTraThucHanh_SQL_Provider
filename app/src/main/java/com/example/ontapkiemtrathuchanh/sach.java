package com.example.ontapkiemtrathuchanh;

public class sach {
    private String maSach, tenSach, maTG;

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public sach(String maSach, String tenSach, String maTG) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maTG = maTG;
    }
}

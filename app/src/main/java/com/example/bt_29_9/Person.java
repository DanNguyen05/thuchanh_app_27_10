package com.example.myapplication.bt_29_9;

// Lớp trừu tượng Person
public abstract class Person {
    private String hoTen;
    private String ngaySinh;
    private String diaChi;

    public Person(String hoTen, String ngaySinh, String diaChi) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
    }

    // Các getter/setter
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    // Phương thức trừu tượng, sẽ được ghi đè
    public abstract String hienThiThongTin();
}
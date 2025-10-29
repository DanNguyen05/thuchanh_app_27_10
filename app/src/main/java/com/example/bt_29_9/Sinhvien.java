package com.example.myapplication.bt_29_9;

// Lớp Sinhvien kế thừa từ Person
public class Sinhvien extends Person {
    private String maSinhVien;
    private String nganhHoc;
    private double diemTrungBinh;

    public Sinhvien(String hoTen, String ngaySinh, String diaChi, String maSinhVien, String nganhHoc, double diemTrungBinh) {
        super(hoTen, ngaySinh, diaChi);
        this.maSinhVien = maSinhVien;
        this.nganhHoc = nganhHoc;
        this.diemTrungBinh = diemTrungBinh;
    }

    // Các getter/setter
    public String getMaSinhVien() { return maSinhVien; }
    public void setMaSinhVien(String maSinhVien) { this.maSinhVien = maSinhVien; }
    public String getNganhHoc() { return nganhHoc; }
    public void setNganhHoc(String nganhHoc) { this.nganhHoc = nganhHoc; }
    public double getDiemTrungBinh() { return diemTrungBinh; }
    public void setDiemTrungBinh(double diemTrungBinh) { this.diemTrungBinh = diemTrungBinh; }

    @Override
    public String hienThiThongTin() {
        return "--- Thong tin Sinh Vien ---\n" +
                "Ma Sinh Vien: " + maSinhVien + "\n" +
                "Ho Ten: " + getHoTen() + "\n" +
                "Ngay Sinh: " + getNgaySinh() + "\n" +
                "Dia Chi: " + getDiaChi() + "\n" +
                "Nganh Hoc: " + nganhHoc + "\n" +
                "Diem Trung Binh: " + diemTrungBinh + "\n" +
                "---------------------------\n";
    }
}
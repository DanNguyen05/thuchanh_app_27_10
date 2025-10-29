package com.example.myapplication.bt_29_9;

// Lớp Giangvien kế thừa từ Person
public class Giangvien extends Person {
    private String maGiangVien;
    private String khoa;
    private double luong;

    public Giangvien(String hoTen, String ngaySinh, String diaChi, String maGiangVien, String khoa, double luong) {
        super(hoTen, ngaySinh, diaChi);
        this.maGiangVien = maGiangVien;
        this.khoa = khoa;
        this.luong = luong;
    }

    // Các getter/setter
    public String getMaGiangVien() { return maGiangVien; }
    public void setMaGiangVien(String maGiangVien) { this.maGiangVien = maGiangVien; }
    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }
    public double getLuong() { return luong; }
    public void setLuong(double luong) { this.luong = luong; }

    @Override
    public String hienThiThongTin() {
        return "--- Thong tin Giang Vien ---\n" +
                "Ma Giang Vien: " + maGiangVien + "\n" +
                "Ho Ten: " + getHoTen() + "\n" +
                "Ngay Sinh: " + getNgaySinh() + "\n" +
                "Dia Chi: " + getDiaChi() + "\n" +
                "Khoa: " + khoa + "\n" +
                "Luong: " + luong + "\n" +
                "----------------------------\n";
    }
}
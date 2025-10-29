# 🚀 HƯỚNG DẪN BUILD VÀ CHẠY APP

## ⭐ BUILD APK MENU (KHUYẾN NGHỊ)

### Cách 1: Từ Android Studio
1. Mở project trong Android Studio
2. Chọn **Build Variants** (góc trái màn hình)
3. Chọn **menuDebug**
4. **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
5. Đợi build xong, click **locate** để mở folder chứa APK

### Cách 2: Command Line
```powershell
# Windows PowerShell
cd d:\app_ungdung
.\gradlew assembleMenuDebug
```

**Output**: `app\build\outputs\apk\menu\debug\app-menu-debug.apk`

## 📱 CÀI ĐẶT APK

### Qua ADB (có kết nối thiết bị/emulator)
```powershell
adb install app\build\outputs\apk\menu\debug\app-menu-debug.apk
```

### Trực tiếp trên điện thoại
1. Copy file `app-menu-debug.apk` sang điện thoại
2. Mở file → Cho phép cài đặt từ nguồn không xác định
3. Cài đặt

## ▶️ CHẠY TRỰC TIẾP TỪ ANDROID STUDIO

1. Chọn **Build Variants** → **menuDebug**
2. Kết nối thiết bị hoặc khởi động emulator
3. Click **Run** ▶ (hoặc **Shift + F10**)

## 🎯 KẾT QUẢ

Sau khi cài đặt, bạn sẽ thấy:
- **1 icon duy nhất** trên launcher: "Menu Ứng Dụng"
- Mở app → 4 card đẹp màu sắc:
  - 🖩 **Máy tính** (đen)
  - 🖼️ **Thư viện** (đỏ)
  - ☀️ **Thời tiết** (xanh dương)
  - ✅ **S-Task** (xanh lá)
- Click vào mỗi card để mở ứng dụng tương ứng

## 📦 BUILD CÁC FLAVOR KHÁC (Tùy chọn)

Nếu muốn build từng ứng dụng riêng lẻ:

```powershell
.\gradlew assembleCalculatorDebug  # Chỉ Máy tính
.\gradlew assembleGalleryDebug     # Chỉ Thư viện
.\gradlew assembleWeatherDebug     # Chỉ Thời tiết
.\gradlew assembleTaskDebug        # Chỉ S-Task
```

**Lưu ý**: Các APK riêng sẽ tạo launcher icon riêng. Khuyến nghị dùng **menu flavor** để có trải nghiệm thống nhất.

## 🔍 XỬ LÝ LỖI

### Lỗi: Gradle sync failed
```powershell
# Clean và rebuild
.\gradlew clean
.\gradlew assembleMenuDebug
```

### Lỗi: SDK not found
- Mở Android Studio → **Tools** → **SDK Manager**
- Cài đặt **Android SDK Platform 36** (hoặc cao hơn)
- Cài đặt **Build Tools 36.x**

### Lỗi: JDK version
- Dự án cần **JDK 11** hoặc cao hơn
- Check: **File** → **Project Structure** → **SDK Location**

## 📋 YÊU CẦU HỆ THỐNG

- **OS**: Windows 10/11, macOS, Linux
- **Android Studio**: Ladybug | 2024.2.1 hoặc mới hơn
- **JDK**: 11 hoặc cao hơn
- **Gradle**: 8.13 (tự động download)
- **Min Android**: 7.0 (API 24)
- **Target Android**: API 36

## 📞 TÀI LIỆU KHÁC

- **README.md** - Tổng quan dự án
- **BAO_CAO_CHI_TIET.md** - Chi tiết kỹ thuật đầy đủ
- **DANH_GIA_4_BAI.md** - Đánh giá theo yêu cầu đề bài

---

**Tip**: Luôn build với **menuDebug** flavor để có app thống nhất với 1 launcher icon duy nhất! 🎉

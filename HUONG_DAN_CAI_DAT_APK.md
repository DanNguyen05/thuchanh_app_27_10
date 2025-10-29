# 📱 HƯỚNG DẪN CÀI ĐẶT APK MỚI

## ⚠️ QUAN TRỌNG: Vấn đề giao diện cũ

Nếu bạn đang thấy **giao diện cũ** trên điện thoại mặc dù code đã có giao diện mới, đó là vì bạn đã cài **APK CŨ** (individual flavor APKs).

### 🔧 Nguyên nhân:
- Dự án có 5 Product Flavors (calculator, gallery, weather, task, menu)
- Mỗi flavor build ra 1 APK riêng
- Bạn đã cài các APK cũ: `calculatorDebug.apk`, `galleryDebug.apk`, v.v.
- Các APK này **KHÔNG CÓ** tính năng mới

### ✅ Giải pháp:

## Bước 1: Gỡ cài đặt APK cũ

Vào điện thoại Android, gỡ cài đặt **TẤT CẢ** các app sau:
- ❌ My Application (Calculator)
- ❌ My Application (Gallery)
- ❌ My Application (Weather)
- ❌ My Application (Task)

**Cách gỡ:**
1. Settings → Apps → Tìm "My Application"
2. Chọn từng app → Uninstall
3. Hoặc: Giữ icon app → App info → Uninstall

## Bước 2: Cài đặt APK mới (menuDebug.apk)

### Tìm file APK:
```
D:\app_ungdung\app\build\outputs\apk\menu\debug\app-menu-debug.apk
```

### Cách cài đặt:

**Phương án 1: Qua USB (Khuyến nghị)**
1. Kết nối điện thoại với máy tính qua USB
2. Bật "USB Debugging" trên điện thoại:
   - Settings → About phone → Tap "Build number" 7 lần
   - Settings → Developer options → Enable "USB debugging"
3. Chạy lệnh (PowerShell):
   ```powershell
   cd D:\app_ungdung
   .\gradlew installMenuDebug
   ```

**Phương án 2: Copy file APK**
1. Copy file `app-menu-debug.apk` vào điện thoại (USB/Drive/Email)
2. Mở File Manager trên điện thoại
3. Tìm file APK → Nhấn cài đặt
4. Cho phép "Install from unknown sources" nếu được hỏi

## Bước 3: Kiểm tra

Sau khi cài xong, bạn sẽ thấy:
- ✅ **1 icon duy nhất**: "Menu Ứng Dụng"
- ✅ Mở app → Thấy 4 nút màu: Calculator, Gallery, Weather, Task
- ✅ Giao diện đẹp, hiện đại với **tất cả tính năng mới**

## 🎨 Các tính năng mới đã thêm:

### 📊 TASK (S-Task) - Nâng cấp mạnh nhất:
- ✅ **Độ ưu tiên** 4 cấp: 🔵 Thấp, 🟡 Trung bình, 🔴 Cao, ⚫ Khẩn cấp
- ✅ **Danh mục** 6 loại: 💼 Công việc, 🏠 Cá nhân, 🛒 Mua sắm, 📚 Học tập, 💪 Sức khỏe, 🎯 Khác
- ✅ **Date Picker** với lịch chọn ngày (không cho chọn quá khứ)
- ✅ **Màu nền CardView** theo độ ưu tiên (đỏ nhạt = khẩn cấp, cam = cao, vàng = trung bình)
- ✅ **Badge emoji** hiển thị độ ưu tiên trên mỗi task
- ✅ **Lọc thông minh**: Tất cả / Chưa xong / Đã xong / Khẩn cấp
- ✅ **Thống kê chi tiết**: Tỷ lệ hoàn thành, phân bổ theo độ ưu tiên
- ✅ **Checkbox animation**: Gạch ngang, làm mờ khi hoàn thành
- ✅ **Notification**: Thông báo khi thêm/xóa task

### 🧮 CALCULATOR (Máy tính):
- ✅ **Lịch sử tính toán**: Lưu 50 phép tính gần nhất
- ✅ **Memory functions**: MC (Clear), MR (Recall), M+ (Add), M- (Subtract)
- ✅ **Menu**: Xem lịch sử, quản lý memory
- ✅ Hiển thị phép tính đầy đủ trong result line

### 🖼️ GALLERY (Thư viện ảnh):
- ✅ **Pinch to Zoom**: Phóng to/thu nhỏ bằng 2 ngón (0.5x → 5x)
- ✅ **Chia sẻ ảnh**: Share qua Facebook, Zalo, Email, v.v.
- ✅ **Thông tin ảnh**: Vị trí, zoom level, kích thước, slideshow status
- ✅ **Reset zoom**: Nút reset về zoom 1x
- ✅ Giao diện nút bấm đẹp (Left/Center/Right với màu xanh/đỏ)

### 🌤️ WEATHER (Thời tiết):
- ✅ **Thành phố yêu thích**: Lưu danh sách thành phố thường xem
- ✅ **Dự báo 7 ngày**: Xem thời tiết cả tuần với emoji và nhiệt độ
- ✅ **Gợi ý thông minh**: Nhắc nhở mang ô khi có mưa
- ✅ **Quick access**: Nhấn vào favorite để xem nhanh

## 🏆 Điểm mạnh để đạt điểm cao:

1. **UI/UX hiện đại**: Material Design + iOS-inspired components
2. **Tính năng phong phú**: Vượt xa yêu cầu cơ bản
3. **Data persistence**: SharedPreferences + Gson JSON
4. **User experience**: Animations, notifications, validations
5. **Architecture**: Clean code, proper separation of concerns
6. **Extra features**: 
   - Task: 6 tính năng nâng cao
   - Calculator: History + Memory
   - Gallery: Zoom + Share
   - Weather: Favorites + 7-day forecast

## 🐛 Khắc phục sự cố:

**Vẫn thấy giao diện cũ?**
- Đảm bảo đã gỡ **TẤT CẢ** app cũ
- Force stop app: Settings → Apps → Force stop
- Clear cache: Settings → Apps → Clear cache
- Khởi động lại điện thoại

**Lỗi "Parse error"?**
- File APK bị lỗi, build lại:
  ```powershell
  .\gradlew clean assembleMenuDebug
  ```

**Không cài được?**
- Cho phép "Install unknown apps" trong Settings
- Kiểm tra dung lượng điện thoại (cần ~20MB)

## 📞 Liên hệ:

Nếu còn vấn đề, hãy kiểm tra:
1. File log: `D:\app_ungdung\build\reports\problems\problems-report.html`
2. Gradle console output
3. Logcat trên Android Studio

---

✅ **Chúc bạn demo thành công và đạt điểm cao!** 🎉

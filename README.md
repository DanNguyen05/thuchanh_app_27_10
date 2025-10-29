# 📱 My Application - Multi-Feature Android App

## 🎯 Tổng quan

Ứng dụng Android tổng hợp 4 chức năng chính:
- 🖩 **Máy tính** - Calculator đầy đủ tính năng
- 🖼️ **Thư viện ảnh** - Gallery với slideshow
- ☀️ **Thời tiết** - Weather forecast cho các thành phố VN
- ✅ **S-Task** - Task manager với notifications

## 📸 Screenshots

### Menu chính
```
┌────────────────────────────────┐
│  Menu Ứng Dụng                 │
│  Chọn ứng dụng bạn muốn sử dụng│
│                                │
│  ┌──────────────────────────┐ │
│  │ 🖩  Máy tính          ›  │ │ ← Đen
│  │    Thực hiện phép tính...│ │
│  └──────────────────────────┘ │
│                                │
│  ┌──────────────────────────┐ │
│  │ 🖼️  Thư viện         ›  │ │ ← Đỏ
│  │    Xem và quản lý hình ảnh│ │
│  └──────────────────────────┘ │
│                                │
│  ┌──────────────────────────┐ │
│  │ ☀️  Thời tiết        ›  │ │ ← Xanh dương
│  │    Dự báo thời tiết...   │ │
│  └──────────────────────────┘ │
│                                │
│  ┌──────────────────────────┐ │
│  │ ✅  S-Task           ›  │ │ ← Xanh lá
│  │    Quản lý công việc...  │ │
│  └──────────────────────────┘ │
└────────────────────────────────┘
```

## 🚀 Cách build và chạy

### 1. Build Menu APK (Khuyến nghị)
```bash
# Windows PowerShell
.\gradlew assembleMenuDebug

# Output:
# app\build\outputs\apk\menu\debug\app-menu-debug.apk
```

### 2. Cài đặt APK
```bash
# Qua ADB
adb install app\build\outputs\apk\menu\debug\app-menu-debug.apk

# Hoặc copy file APK sang điện thoại và cài đặt trực tiếp
```

### 3. Chạy từ Android Studio
1. Mở **Build Variants**
2. Chọn **menuDebug**
3. Click **Run** ▶

## 📦 Product Flavors

Dự án hỗ trợ 5 flavors:

| Flavor | Mô tả | Build Command |
|--------|-------|---------------|
| **menu** | ⭐ Menu thống nhất (khuyến nghị) | `.\gradlew assembleMenuDebug` |
| calculator | Chỉ Calculator | `.\gradlew assembleCalculatorDebug` |
| gallery | Chỉ Gallery | `.\gradlew assembleGalleryDebug` |
| weather | Chỉ Weather | `.\gradlew assembleWeatherDebug` |
| task | Chỉ Task | `.\gradlew assembleTaskDebug` |

## 🏗️ Kiến trúc

```
app/
├── src/
│   ├── main/                    # Base code
│   │   ├── java/
│   │   │   ├── MenuActivity.kt
│   │   │   └── ui/
│   │   │       ├── CalculatorActivity.kt
│   │   │       ├── GalleryActivity.kt
│   │   │       ├── WeatherActivity.kt
│   │   │       └── TaskActivity.kt
│   │   └── res/layout/
│   │       └── activity_menu.xml
│   │
│   ├── menu/                    # Menu flavor
│   │   └── AndroidManifest.xml
│   │
│   ├── calculator/              # Calculator flavor
│   │   └── AndroidManifest.xml
│   │
│   ├── gallery/                 # Gallery flavor
│   │   └── AndroidManifest.xml
│   │
│   ├── weather/                 # Weather flavor
│   │   └── AndroidManifest.xml
│   │
│   └── task/                    # Task flavor
│       └── AndroidManifest.xml
│
└── build.gradle.kts
```

## 🎨 Design

### Màu sắc
- **Calculator**: Black (#1C1C1E) - iOS style
- **Gallery**: Red (#FF3B30) - Vibrant
- **Weather**: Blue (#007AFF) - Sky blue
- **Task**: Green (#34C759) - Success color

### Components
- Material Design CardView
- Custom gradients cho Weather/Task
- Smooth animations (fade, slide)
- Modern iOS-inspired interface

## ✨ Tính năng

### 🖩 Máy tính
- Phép tính cơ bản: +, -, ×, ÷
- Phần trăm (%)
- Đổi dấu (±)
- Số thập phân
- Giao diện giống iOS Calculator

### 🖼️ Thư viện ảnh
- Grid 3 cột hiển thị thumbnails
- ImageSwitcher với animation
- Slideshow tự động (2s/ảnh)
- Navigation: Previous/Next
- 9 ảnh mẫu

### ☀️ Thời tiết
- Chọn thành phố (8 thành phố VN)
- Hiển thị:
  - Nhiệt độ
  - Độ ẩm
  - Tốc độ gió
  - Tầm nhìn
  - Mô tả thời tiết
- UI gradient đẹp mắt

### ✅ S-Task
- Thêm/sửa/xóa công việc
- Đánh dấu hoàn thành
- Lưu trữ local (SharedPreferences + Gson)
- Thông báo khi thêm task
- RecyclerView với smooth scrolling
- Search bar (UI only)

## 🛠️ Công nghệ

- **Language**: Kotlin
- **UI**: XML layouts + Jetpack Compose (một số màn hình)
- **Build**: Gradle Kotlin DSL
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36

### Dependencies
- AndroidX Core KTX
- AppCompat
- Material Components
- RecyclerView
- Gson (JSON serialization)
- Navigation Component

## 📚 Tài liệu

- [HOW_TO_BUILD.md](HOW_TO_BUILD.md) - Hướng dẫn build chi tiết
- [BAO_CAO_CHI_TIET.md](BAO_CAO_CHI_TIET.md) - Báo cáo kỹ thuật đầy đủ

## ⚠️ Lưu ý

1. **Build flavor menu**: Để có trải nghiệm tốt nhất, luôn build với **menuDebug** hoặc **menuRelease**
2. **Xóa app cũ**: Nếu trước đó đã cài các APK riêng lẻ, hãy gỡ chúng trước
3. **ADB**: Cần cài Android SDK và enable USB Debugging để dùng ADB

## 🐛 Known Issues

- [ ] Weather data là mock data (không kết nối API thật)
- [ ] Gallery dùng system icons thay vì ảnh thật
- [ ] Task search bar chỉ là UI, chưa implement search
- [ ] Không có unit tests

## 🔮 Roadmap

- [ ] Tích hợp Weather API thực
- [ ] Upload ảnh thật vào Gallery
- [ ] Implement search trong Task
- [ ] Thêm Dark Mode
- [ ] Settings screen
- [ ] Localization (EN, VN)
- [ ] Unit tests & UI tests

## 👨‍💻 Development

### Clone repository
```bash
git clone <repo-url>
cd app_ungdung
```

### Open trong Android Studio
1. File → Open
2. Chọn thư mục `app_ungdung`
3. Wait for Gradle sync
4. Select **menuDebug** variant
5. Run!

### Build tất cả flavors
```powershell
.\gradlew assembleDebug
```

## 📄 License

Dự án này là bài tập học tập, không có license cụ thể.

## 🙏 Credits

- Design inspiration: iOS Human Interface Guidelines
- Icons: Material Design Icons
- Colors: iOS System Colors

---

**Last updated**: 28/10/2025  
**Version**: 1.0-menu

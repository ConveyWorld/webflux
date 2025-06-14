# Account Management System

## 📝 Giới thiệu

Dự án này xây dựng một hệ thống quản lý tài khoản (Account Management System) sử dụng công nghệ hiện đại, hỗ trợ đồng thời **REST API** và **GraphQL API** trên nền tảng reactive, đảm bảo hiệu suất cao và khả năng mở rộng tốt.

Ứng dụng cho phép:
- Tạo mới tài khoản
- Cập nhật thông tin tài khoản
- Tìm kiếm tài khoản theo ID
- Xóa tài khoản
- Truy vấn linh hoạt qua GraphQL hoặc REST

---

## 🛠️ Công nghệ sử dụng

| Công nghệ | Mô tả |
|:----------|:------|
| **Spring Boot 3** | Framework Java hiện đại, tối ưu phát triển microservices |
| **Spring WebFlux** | Lập trình reactive, non-blocking |
| **Spring Data R2DBC** | Reactive database access |
| **Spring GraphQL** | Xây dựng API GraphQL hiệu quả |
| **Spring WebFlux REST** | Triển khai API REST reactive |
| **PostgreSQL** | Cơ sở dữ liệu quan hệ, hỗ trợ mạnh mẽ cho các ứng dụng lớn |
| **Project Reactor** | Core library cho reactive programming trong Java |
| **Lombok** | Giảm thiểu boilerplate code (getter/setter, constructor...) |

---

## 🗄️ Cấu trúc dự án

```bash
src/main/java/com/example/account
    ├── controller       # REST & GraphQL API controllers
    ├── service          # Business logic layer
    ├── repository       # Reactive database access
    ├── model            # Entity classes (Account, etc.)
    └── config           # Cấu hình ứng dụng (GraphQL, Database, Exception Handler)

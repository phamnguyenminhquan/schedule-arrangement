# Hệ thống tài liệu Class Diagrams

## 1. Tầng dữ liệu & thực thể (models)
* [Xem sơ đồ models](models-class.mmd) - Định nghĩa các domain models `Slot`, `Subject`, `Student`, `Section`.

## 2. Tầng quản lý lưu trữ (repositories)
* [Xem sơ đồ repositories](repositories-class.mmd) - Định nghĩa các repo `SubjectRepo`, `StudentRepo`, `SectionRepo`.

## 3. Tầng logics nghiệp vụ (services)
* [Xem sơ đồ responses](responses-class.mmd) - Định nghĩa **Result Pattern** để báo lỗi thay vì lạm dụng **Exception**.
* [Xem sơ đồ registrationCheckers](registration-checkers-class.mmd) - Định nghĩa các checkers để kiểm tra và trả về kết quả.
* [Xem sơ đồ registrationServices](registrationService-class.mmd) - Định nghĩa luồng xử lý chính của **registrationService**.

## Tổng quan các mối quan hệ giữa các thành phần (Overall Relationship)
* [Xem sơ đồ main relationship](main-relationship-class.mmd) - Thể hiện **relationship** giữ các class/module.
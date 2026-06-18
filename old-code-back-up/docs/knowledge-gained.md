# Tổng hợp kiến thức tích lũy (Knowledge Gained)

## Architecture
* Phân thành các folder đảm nhiệm các chức năng khác nhau:
  - `models` : nơi định nghĩa các thực thể chính (domain model).
  - `repositories` : nơi định nghĩa các kho lưu trữ dữ, quản lý dữ liệu.
  - `services` : nơi định nghĩa các tầng logics nghiệp vụ riêng biệt.

## Class Define
- Sử dụng `final` để đảm bảo tính bất biến **immuntability**, ổn định của các thuộc tính bên trong đối tượng.
- `throw IllegalArgumentException` để **validate** dữ liệu vào:
  - e.g: Bên trong `Slot` constructor, ngăn không cho thuộc tính `null` và đảm bảo `startTime.isBefore(endTime)`.

## Repositories
- Sử dụng `Map<id, Object>` để tối ưu hiệu quả tìm kiếm O(1).
- Sử dụng `ConcurrentHashMap` thay vì `HashMap` để đảm bảo **Thread-safe** (trường hợp nhiều người cùng gọi repo xử lý cùng 1 lúc).

## Services
- Áp dụng **Strategy Pattern** để gọi lần lượt các **registrationChecker** ra xử lý dữ liệu.
- Đảm bảo **Open/Closed Principle** qua việc tạo `interface RegistrationChecker` rồi mới viết chi tiết các **checkers** khác như **SectionCapacityChecker**, **ScheduleConflictChecker**, etc.
- Áp dụng **Dependency Injection** để thêm các **repositories** cần thiết vào các **checkers** thông qua **constructor**, đồng thời đảm bảo được **Dependency Inversion Principle**.
- Áp dụng **Result Pattern/Notification Pattern** để làm giá trị trả về cho các **checkers** thay vì lạm dụng **Exception**.
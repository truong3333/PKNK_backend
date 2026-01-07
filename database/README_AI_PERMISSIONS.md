# Hướng Dẫn Thêm AI Permissions

## Chạy SQL Script

Để thêm permissions cho AI Service, chạy file SQL sau:

```bash
mysql -u root -p dental_clinic < add_ai_permissions.sql
```

Hoặc chạy trực tiếp trong MySQL:

```sql
USE dental_clinic;
SOURCE add_ai_permissions.sql;
```

## Permissions Được Thêm

1. **ANALYZE_IMAGE**: Phân tích ảnh X-Quang và CT Scan bằng AI
   - Được gán cho: DOCTOR, DOCTORLV2

2. **VIEW_AI_ANALYSIS**: Xem kết quả phân tích AI
   - Được gán cho: DOCTOR, DOCTORLV2, NURSE, PATIENT

## Kiểm Tra

Sau khi chạy script, kiểm tra bằng:

```sql
-- Kiểm tra permissions
SELECT * FROM permission WHERE name IN ('ANALYZE_IMAGE', 'VIEW_AI_ANALYSIS');

-- Kiểm tra role permissions
SELECT rp.roles_name, rp.permissions_name, p.description
FROM role_permissions rp
JOIN permission p ON rp.permissions_name = p.name
WHERE p.name IN ('ANALYZE_IMAGE', 'VIEW_AI_ANALYSIS')
ORDER BY rp.roles_name, rp.permissions_name;
```



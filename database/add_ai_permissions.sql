-- Add AI Service Permissions
-- Run this script to add ANALYZE_IMAGE and VIEW_AI_ANALYSIS permissions

USE dental_clinic;

-- Add new permissions
INSERT INTO `permission` (`name`, `description`) VALUES
('ANALYZE_IMAGE', 'Phân tích ảnh X-Quang và CT Scan bằng AI'),
('VIEW_AI_ANALYSIS', 'Xem kết quả phân tích AI')
ON DUPLICATE KEY UPDATE `description` = VALUES(`description`);

-- Assign permissions to roles
-- DOCTOR and DOCTORLV2 can analyze images and view results
INSERT INTO `role_permissions` (`roles_name`, `permissions_name`) VALUES
('DOCTOR', 'ANALYZE_IMAGE'),
('DOCTORLV2', 'ANALYZE_IMAGE'),
('DOCTOR', 'VIEW_AI_ANALYSIS'),
('DOCTORLV2', 'VIEW_AI_ANALYSIS')
ON DUPLICATE KEY UPDATE `roles_name` = VALUES(`roles_name`);

-- NURSE can view AI analysis results
INSERT INTO `role_permissions` (`roles_name`, `permissions_name`) VALUES
('NURSE', 'VIEW_AI_ANALYSIS')
ON DUPLICATE KEY UPDATE `roles_name` = VALUES(`roles_name`);

-- PATIENT can view their own AI analysis results
INSERT INTO `role_permissions` (`roles_name`, `permissions_name`) VALUES
('PATIENT', 'VIEW_AI_ANALYSIS')
ON DUPLICATE KEY UPDATE `roles_name` = VALUES(`roles_name`);



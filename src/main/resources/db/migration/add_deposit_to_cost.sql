-- Migration: Add deposit support to cost table
-- Add type column to distinguish deposit, phase_payment, and examination
ALTER TABLE cost
ADD COLUMN type VARCHAR(30) NOT NULL DEFAULT 'phase_payment',
ADD COLUMN treatment_plan_id VARCHAR(255) NULL;

-- Add foreign key constraint
ALTER TABLE cost
ADD CONSTRAINT FK_cost_treatment_plans
FOREIGN KEY (treatment_plan_id) REFERENCES treatment_plans(id);

-- Set type for existing cost records
-- If cost.id matches a treatment_phase.id, set type = 'phase_payment'
-- If cost.id matches an examination.id, set type = 'examination'
-- Note: This requires checking against treatment_phases and examination tables
-- For now, default to 'phase_payment' for backward compatibility
UPDATE cost SET type = 'phase_payment' WHERE type IS NULL OR type = '';

-- Update treatment_plan_id for phase_payment costs
UPDATE cost c
INNER JOIN treatment_phases tp ON c.id = tp.id
SET c.treatment_plan_id = tp.treatment_plans_id
WHERE c.type = 'phase_payment';

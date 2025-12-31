-- SQL script to create and populate tables for the demo2 project
-- MySQL-compatible
-- Tables covered: warehouses, staffs, parcels, staffs_role (element collection for Staff.role)

-- Notes:
-- 1) This script assumes enums are stored as ORDINAL (the project's entities use @Enumerated with no value -> ORDINAL).
--    ParcelStatus ordinals: PENDING=0, IN_TRANSIT=1, DELIVERED=2, RETURNED=3
--    Role ordinals: ADMIN=0, STAFF=1, USER=2
-- 2) If your application already created these tables via JPA, you can remove the CREATE TABLE/DROP statements and only run the INSERTs.
-- 3) To import: mysql -u myuser -p mydb < generate_data.sql

DROP TABLE IF EXISTS staffs_role;
DROP TABLE IF EXISTS parcels;
DROP TABLE IF EXISTS staffs;
DROP TABLE IF EXISTS warehouses;

CREATE TABLE warehouses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code BIGINT,
  address VARCHAR(255),
  capacity INT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE staffs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255),
  password VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE parcels (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tracking_code BIGINT,
  sender VARCHAR(255),
  receiver VARCHAR(255),
  weight DOUBLE,
  status INT,
  warehouse_id BIGINT,
  CONSTRAINT fk_parcels_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Element collection table for Staff.role (List<Role>)
CREATE TABLE staffs_role (
  staffs_id BIGINT NOT NULL,
  role INT,
  CONSTRAINT fk_staffs_role_staff FOREIGN KEY (staffs_id) REFERENCES staffs (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert sample warehouses
INSERT INTO warehouses (code, address, capacity) VALUES
(1001, '123 Main St, Springfield', 500),
(1002, '200 Warehouse Rd, Shelbyville', 300),
(1003, '55 Distribution Ave, Capital City', 1000);

-- Insert sample staffs
INSERT INTO staffs (username, password) VALUES
('alice', 'alicepwd'),
('bob', 'bobpwd'),
('carol', 'carolpwd');

-- Assign roles using ORDINAL values (ADMIN=0, STAFF=1, USER=2)
-- alice = ADMIN + STAFF, bob = STAFF, carol = USER
INSERT INTO staffs_role (staffs_id, role) VALUES
(1, 0),
(1, 1),
(2, 1),
(3, 2);

-- Insert sample parcels (status uses ORDINAL: PENDING=0, IN_TRANSIT=1, DELIVERED=2, RETURNED=3)
INSERT INTO parcels (tracking_code, sender, receiver, weight, status, warehouse_id) VALUES
(9000000001, 'Company A', 'John Doe', 2.5, 0, 1),
(9000000002, 'Company B', 'Jane Smith', 5.75, 1, 1),
(9000000003, 'Online Shop', 'Alice Johnson', 1.2, 2, 2),
(9000000004, 'Marketplace', 'Bob Brown', 10.0, 1, 3),
(9000000005, 'Vendor X', 'Carol White', 0.75, 0, 3);

-- Optional: show inserted rows
SELECT * FROM warehouses;
SELECT * FROM staffs;
SELECT * FROM staffs_role;
SELECT * FROM parcels;

select p1_0.id,p1_0.receiver,p1_0.sender,p1_0.status,p1_0.tracking_code,p1_0.warehouse_id,p1_0.weight from parcels p1_0
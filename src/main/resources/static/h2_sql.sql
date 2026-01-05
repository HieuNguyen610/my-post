-- sql
SET MODE MYSQL;

DROP TABLE IF EXISTS staffs_role;
DROP TABLE IF EXISTS parcels;
DROP TABLE IF EXISTS staffs;
DROP TABLE IF EXISTS warehouses;

CREATE TABLE warehouses (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            code BIGINT,
                            address VARCHAR(255),
                            capacity INT
);

CREATE TABLE staffs (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255),
                        password VARCHAR(255)
);

CREATE TABLE parcels (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         tracking_code BIGINT,
                         sender VARCHAR(255),
                         receiver VARCHAR(255),
                         weight DOUBLE,
                         status INT,
                         warehouse_id BIGINT,
                         CONSTRAINT fk_parcels_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id) ON DELETE SET NULL
);

CREATE TABLE staffs_role (
                             staffs_id BIGINT NOT NULL,
                             role INT,
                             CONSTRAINT fk_staffs_role_staff FOREIGN KEY (staffs_id) REFERENCES staffs(id) ON DELETE CASCADE
);

INSERT INTO warehouses (code, address, capacity) VALUES
                                                     (1001, '123 Main St, Springfield', 500),
                                                     (1002, '200 Warehouse Rd, Shelbyville', 300),
                                                     (1003, '55 Distribution Ave, Capital City', 1000);

INSERT INTO staffs (username, password) VALUES
                                            ('alice', 'alicepwd'),
                                            ('bob', 'bobpwd'),
                                            ('carol', 'carolpwd');

INSERT INTO staffs_role (staffs_id, role) VALUES
                                              (1, 0),
                                              (1, 1),
                                              (2, 1),
                                              (3, 2);

INSERT INTO parcels (tracking_code, sender, receiver, weight, status, warehouse_id) VALUES
                                                                                        (9000000001, 'Company A', 'John Doe', 2.5, 0, 1),
                                                                                        (9000000002, 'Company B', 'Jane Smith', 5.75, 1, 1),
                                                                                        (9000000003, 'Online Shop', 'Alice Johnson', 1.2, 2, 2),
                                                                                        (9000000004, 'Marketplace', 'Bob Brown', 10.0, 1, 3),
                                                                                        (9000000005, 'Vendor X', 'Carol White', 0.75, 0, 3);

-- Optional verification queries
SELECT * FROM warehouses;
SELECT * FROM staffs;
SELECT * FROM staffs_role;
SELECT * FROM parcels;

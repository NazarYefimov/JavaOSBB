CREATE TABLE IF NOT EXISTS OSBBMembers (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Ownership (
    ownership_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT,
    apartment_id INT,
    FOREIGN KEY (member_id) REFERENCES OSBBMembers(member_id),
    FOREIGN KEY (apartment_id) REFERENCES Apartments(apartment_id)
);

CREATE TABLE IF NOT EXISTS Apartments (
    apartment_id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT,
    apartment_number INT,
    area DOUBLE,
    FOREIGN KEY (building_id) REFERENCES Buildings(building_id)
);

CREATE TABLE IF NOT EXISTS Buildings (
    building_id INT PRIMARY KEY AUTO_INCREMENT,
    building_number INT
);

CREATE TABLE IF NOT EXISTS Residents (
    resident_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT,
    car_access INT,
    FOREIGN KEY (member_id) REFERENCES OSBBMembers(member_id)
);
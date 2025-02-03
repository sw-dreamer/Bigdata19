create database IF NOT EXISTS contact;

use contact;

CREATE TABLE IF NOT EXISTS relationT (
    relationNo VARCHAR(50) NOT NULL,
    relationship ENUM('가족', '친구', '기타') NOT NULL,
    PRIMARY KEY (relationNo)
);

CREATE TABLE IF NOT EXISTS peopleT (
    id INT AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    phoneNumber VARCHAR(11) NOT NULL,
    address VARCHAR(50) NOT NULL,
    relationNo VARCHAR(50) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  
    UNIQUE KEY (phoneNumber),
    PRIMARY KEY (id), 
    CONSTRAINT fk_relation FOREIGN KEY (relationNo) REFERENCES relationT(relationNo) ON DELETE RESTRICT ON UPDATE CASCADE
);


INSERT INTO relationT (relationNo, relationship)
VALUES
    ('1', '가족'),
    ('2', '친구'),
    ('3', '기타')
;

commit;


create database phone;


use phone; 

show tables;

drop database phone;

CREATE TABLE members (
	memberno INT AUTO_INCREMENT,
    memberid VARCHAR(255) not null,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  
    PRIMARY KEY(memberNo),
    Unique Key (memberId)
);


CREATE TABLE IF NOT EXISTS moim (
    gubun_id VARCHAR(255) NOT NULL,
    gubunName ENUM('가족', '친구', '기타') NOT NULL,
    PRIMARY KEY (gubun_id)
);

INSERT INTO moim (gubun_id, gubunName)
VALUES
    ('1', '가족'),
    ('2', '친구'),
    ('3', '기타')
;

CREATE TABLE memberscontacts (
    contactId int AUTO_INCREMENT,  
    name VARCHAR(255) NOT NULL,
    phonenumber VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    gubun_id VARCHAR(255),
    photo BLOB NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (contactId),
    FOREIGN KEY (gubun_id) REFERENCES moim(gubun_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE membercontactmap (
    memberContactNo INT AUTO_INCREMENT,
    memberid VARCHAR(255),
    contactid INT,  
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(memberContactNo),
    FOREIGN KEY (memberid) REFERENCES members(memberId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (contactid) REFERENCES memberscontacts(contactId) ON DELETE CASCADE ON UPDATE CASCADE  
);





commit;


select * from memberscontacts;

INSERT INTO memberscontacts (name, phonenumber, address, gubunId, photo)
VALUES ('허준', '01012345678', '서울시 종로구', '1', '허준.jpg');

select mc.contactId,mc.name, mc.phonenumber, mc.address, mc.photo, mi.gubunName  from memberscontacts mc
inner join moim mi WHERE mi.gubunId = mc.gubunId ;

SELECT mc.contactId, mc.name, mc.phonenumber, mc.address, mc.photo, mi.gubunName
FROM memberscontacts mc
INNER JOIN moim mi
ON mi.gubunId = mc.gubunId;


select * from members;
select * from membercontactmap;
select * from memberscontacts;
select * from moim;

delete from memberscontacts where contactId = 3;
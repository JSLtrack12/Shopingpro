CREATE TABLE ShopMember (
    userid NUMBER PRIMARY KEY,
    username VARCHAR2(255) NOT NULL,
    address VARCHAR2(255) NOT NULL,
    tel VARCHAR2(50) NOT NULL,
    email VARCHAR2(255) UNIQUE NOT NULL,
    pass VARCHAR2(255) NOT NULL,
    subdate DATE
);


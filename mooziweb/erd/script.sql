CREATE TABLE Categories (
                            CategoryID		INT	auto_increment,
                            CategoryName	varchar(50),

                            CONSTRAINT pk_Categories PRIMARY KEY(CategoryID)
);

CREATE TABLE CategoriesForProducts (
                                       CategoryID INT,
                                       ProductID INT,

                                       CONSTRAINT pk_CategoriesForProducts PRIMARY KEY(CategoryID, ProductID),
                                       CONSTRAINT fk_CategoriesForProducts_Categories FOREIGN KEY(CategoryID) REFERENCES Categories(CategoryID),
                                       CONSTRAINT fk_CategoriesForProducts_Products FOREIGN KEY(ProductID) REFERENCES Products(ProductID)
);

CREATE TABLE Products (
                          ProductID	INT	auto_increment,
                          ModelNumber	nvarchar(10),
                          ModelName	nvarchar(120),
                          ProductImage	nvarchar(30),
                          UnitCost	decimal(15),
                          Description	text,
                          ViewCount INT,
                          Quantity INT,

                          CONSTRAINT pk_Products PRIMARY KEY(ProductID)
);

CREATE TABLE `Users` (
                         `UserID` varchar(50) NOT NULL COMMENT '아이디',
                         `UserName` varchar(50) NOT NULL COMMENT '이름',
                         `UserPassword` varchar(200) NOT NULL COMMENT 'mysql password 사용',
                         `UserBirth` varchar(8) NOT NULL COMMENT '생년월일 : 19840503',
                         `UserAuth` varchar(10) NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
                         `CreatedAt` datetime NOT NULL COMMENT '가입일자',
                         `LatestLoginAt` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',

                         PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

CREATE TABLE UserAddress (
                             AddressID INT auto_increment,
                             UserID varchar(50),
                             Name varchar(50),
                             Zipcode varchar(6),
                             Address varchar(300),
                             AddressDetail varchar(100),
                             PhoneNumber varchar(15),

                             CONSTRAINT pk_UserAddress PRIMARY KEY(AddressID),
                             CONSTRAINT fk_UserAddress_UserID FOREIGN KEY(UserID) REFERENCES Users(UserID)
)

CREATE TABLE `UserPointInfo` (
                                 `PointInfoID`	INT	NOT NULL auto_increment,
                                 `UserID`	varchar(50)	NOT NULL	COMMENT '아이디',
                                 `PointInfo`	varchar(30)	NULL,
                                 `Point`	INT	NULL,
                                 `PointDate`	Datetime	NULL,

                                 CONSTRAINT pk_UserPointInfo PRIMARY KEY(`PointInfoID`),
                                 CONSTRAINT fk_UserPointInfo_UserID FOREIGN KEY(UserID) REFERENCES Users(UserID)
);

CREATE TABLE Reviews (
                         ReviewID	int auto_increment,
                         ProductID	int,
                         UserID	varchar(50),
                         Rating		int,
                         Comments	text,

                         CONSTRAINT pk_ReviewID PRIMARY KEY(ReviewID),
                         CONSTRAINT fk_Reviews_ProductID FOREIGN KEY(ProductID) REFERENCES Products(ProductID),
                         CONSTRAINT fk_Reviews_UserID FOREIGN KEY(UserID) REFERENCES Users(UserID)
);

CREATE TABLE Orders (
                        OrderID		int auto_increment,
                        UserID	varchar(50),
                        OrderDate	Datetime,
                        ShipDate	Datetime,

                        CONSTRAINT pk_Orders PRIMARY KEY(OrderID),
                        CONSTRAINT fk_Orders_UserID FOREIGN KEY(UserID) REFERENCES Users(UserID)
);

CREATE TABLE OrderDetails (
                              OrderID		int,
                              ProductID	int,
                              Quantity	int,
                              UnitCost	decimal(15),

                              CONSTRAINT pk_OrderDetails PRIMARY KEY(OrderID, ProductID),
                              CONSTRAINT fk_OrderDetails_Orders FOREIGN KEY(OrderID) REFERENCES Orders(OrderID),
                              CONSTRAINT fk_OrderDetails_Products FOREIGN KEY(ProductID) REFERENCES Products(ProductID)
);

CREATE TABLE ShoppingCart (
                              RecordID	int	auto_increment,
                              UserID varchar(50),
                              Quantity	int,
                              ProductID	int,
                              DateCreated	Datetime DEFAULT NOW(),

                              CONSTRAINT pk_RecordID PRIMARY KEY(RecordID),
                              CONSTRAINT fk_cart_ProductID FOREIGN KEY(ProductID) REFERENCES Products(ProductID),
                              CONSTRAINT fk_cart_UserID FOREIGN KEY(UserID) REFERENCES Users(UserID)
);

ALTER TABLE CategoriesForProducts DROP FOREIGN KEY fk_CategoriesForProducts_Categories;
ALTER TABLE CategoriesForProducts ADD CONSTRAINT fk_CategoriesForProducts_Categories FOREIGN KEY (CategoryID) REFERENCES nhn_academy_11.Categories(CategoryID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE CategoriesForProducts DROP FOREIGN KEY fk_CategoriesForProducts_Products;
ALTER TABLE CategoriesForProducts ADD CONSTRAINT fk_CategoriesForProducts_Products FOREIGN KEY (ProductID) REFERENCES nhn_academy_11.Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Reviews DROP FOREIGN KEY fk_Reviews_ProductID;
ALTER TABLE Reviews ADD CONSTRAINT fk_Reviews_ProductID FOREIGN KEY (ProductID) REFERENCES nhn_academy_11.Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE Reviews DROP FOREIGN KEY fk_Reviews_UserID;
ALTER TABLE Reviews ADD CONSTRAINT fk_Reviews_UserID FOREIGN KEY (UserID) REFERENCES nhn_academy_11.Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ShoppingCart DROP FOREIGN KEY fk_cart_ProductID;
ALTER TABLE ShoppingCart ADD CONSTRAINT fk_cart_ProductID FOREIGN KEY (ProductID) REFERENCES nhn_academy_11.Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE ShoppingCart DROP FOREIGN KEY fk_cart_UserID;
ALTER TABLE ShoppingCart ADD CONSTRAINT fk_cart_UserID FOREIGN KEY (UserID) REFERENCES nhn_academy_11.Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE UserAddress DROP FOREIGN KEY fk_UserAddress_UserID;
ALTER TABLE UserAddress ADD CONSTRAINT fk_UserAddress_UserID FOREIGN KEY (UserID) REFERENCES nhn_academy_11.Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE UserPointInfo DROP FOREIGN KEY fk_UserPointInfo_UserID;
ALTER TABLE UserPointInfo ADD CONSTRAINT fk_UserPointInfo_UserID FOREIGN KEY (UserID) REFERENCES nhn_academy_11.Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Orders DROP FOREIGN KEY fk_Orders_UserID;
ALTER TABLE Orders ADD CONSTRAINT fk_Orders_UserID FOREIGN KEY (UserID) REFERENCES nhn_academy_11.Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE OrderDetails DROP FOREIGN KEY fk_OrderDetails_Orders;
ALTER TABLE OrderDetails ADD CONSTRAINT fk_OrderDetails_Orders FOREIGN KEY (OrderID) REFERENCES nhn_academy_11.Orders(OrderID) ON DELETE CASCADE ON UPDATE CASCADE;
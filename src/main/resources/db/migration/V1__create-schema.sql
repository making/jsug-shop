CREATE TABLE account (
  email     VARCHAR(255) PRIMARY KEY,
  password  VARCHAR(255),
  name      VARCHAR(128),
  birth_day DATE,
  zip       VARCHAR(32),
  address   VARCHAR(255)
);

CREATE TABLE category (
  category_id   INT PRIMARY KEY,
  category_name VARCHAR(128)
);

CREATE TABLE goods (
  goods_id    VARCHAR(36) PRIMARY KEY,
  goods_name  VARCHAR(255),
  description VARCHAR(512),
  category_id INT,
  price       INT,
  FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE ordr (
  order_id   VARCHAR(36) PRIMARY KEY,
  email      VARCHAR(255),
  order_date DATE
);

CREATE TABLE order_line (
  order_id VARCHAR(36),
  line_no  INT,
  goods_id VARCHAR(36),
  quantity INT,
  PRIMARY KEY (order_id, line_no),
  FOREIGN KEY (order_id) REFERENCES ordr (order_id),
  FOREIGN KEY (goods_id) REFERENCES goods (goods_id)
);
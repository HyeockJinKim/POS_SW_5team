# POS_SW_5team
POS

DATABASE SQL

```sql
CREATE TABLE PRODUCT (
  barcode INT PRIMARY KEY,
  productName VARCHAR(50),
  price INT
);

CREATE TABLE SALES_RECORD (
  salesNumber SERIAL PRIMARY KEY,
  salesTime VARCHAR(40),
  salesMoney INT,
  ISCASH BOOLEAN
);

CREATE TABLE SALES (
  barcode INT REFERENCES PRODUCT(barcode) ,
  salesNumber INT REFERENCES SALES_RECORD(salesNumber),
  quantity INT,
  PRIMARY KEY(barcode, salesNumber)
);

INSERT INTO public.product (barcode, productname, price) VALUES (201502043, '바밤바', 500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502044, '구구콘', 1500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502045, '빵또아', 1500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502046, '수박바', 500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502047, '스크류바', 500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502048, '핫바', 1200);
INSERT INTO public.product (barcode, productname, price) VALUES (201502049, '나랑드사이다', 500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502050, '수박바젤리', 700);
INSERT INTO public.product (barcode, productname, price) VALUES (201502051, '요맘때', 700);
INSERT INTO public.product (barcode, productname, price) VALUES (201502052, '요맘때젤리', 1200);
INSERT INTO public.product (barcode, productname, price) VALUES (201502053, '아이셔', 300);
INSERT INTO public.product (barcode, productname, price) VALUES (201502054, '김밥', 1500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502055, '하리보젤리', 1500);
INSERT INTO public.product (barcode, productname, price) VALUES (201502056, '삼각김밥', 1000);
INSERT INTO public.product (barcode, productname, price) VALUES (201502057, '포켓몬빵', 1000);
INSERT INTO public.product (barcode, productname, price) VALUES (201502058, '옥수수빵', 1000);
```

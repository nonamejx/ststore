-- unit table
insert into unit(id, unit_name) values (1, 'Cái');
insert into unit(id, unit_name) values (2, 'Kg');
insert into unit(id, unit_name) values (3, 'Mét');
insert into unit(id, unit_name) values (4, 'Bao');

-- category table
insert into category(id, category_name, father_id) values (1, 'Điện nước', null);
insert into category(id, category_name, father_id) values (2, 'Ống bình minh', 1);
insert into category(id, category_name, father_id) values (3, 'Ống thường', 1);
insert into category(id, category_name, father_id) values (4, 'Bóng đèn', 1);
insert into category(id, category_name, father_id) values (5, 'Điện nhà tắm', 1);
insert into category(id, category_name, father_id) values (6, 'Vật dụng nước nóng', 1);
insert into category(id, category_name, father_id) values (7, 'Ống dài', 2);
insert into category(id, category_name, father_id) values (8, 'Ống to', 2);
insert into category(id, category_name, father_id) values (9, 'Đá', null);
insert into category(id, category_name, father_id) values (10, 'Khác', null);

-- product table
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (1, 'Thép cây Việt Nhật D16', 238450.00, 953800.00, 'Thép cây Việt Nhật D16', 7, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (2, 'Đá hồng Gia Lai', 266000.00, 532000.00, 'Đá hồng Gia Lai', 5, 4);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (3, 'Thép Việt Ý - Thép cây D16', 239400.00, 478800.00, 'Thép Việt Ý - Thép cây D16', 4, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (4, 'Sắt cuộn phi 6', 11400.00, 22800.00, 'Sắt cuộn phi 6', 6, 4);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (5, 'Đá đen Huế', 418000.00, 836000.00, 'Đá đen Huế', 7, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (6, 'Ống hơi Sang-A Φ10', 15200.00, 30400.00, 'Ống hơi Sang-A Φ10', 4, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (7, 'Ống Sang-A U0604', 6650.00, 13300.00, 'Ống Sang-A U0604', 5, 3);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (8, 'Nhựa đường đóng thùng IRAN M60/70', 13680.00, 27360.00, 'Nhựa đường đóng thùng IRAN M60/70', 10, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (9, 'Ống nhựa xoắn HDPE VCOM', 13219.00, 26438.00, 'Ống nhựa xoắn HDPE VCOM', 8, 2);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (10, 'Xe phun tưới nhựa đường', 5700.00, 23123.00, 'Xe phun tưới nhựa đường', 6, 2);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (11, 'Tấm cách nhiệt', 10450.00, 20900.00, 'Tấm cách nhiệt', 7, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (12, 'Cửa kéo Đài Loan', 351500.00, 703000.00, 'Cửa kéo Đài Loan', 4, 4);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (13, 'Sàn gỗ QUEEN FLOOR 12mm', 15750.00, 31500.00, 'Sàn gỗ QUEEN FLOOR 12mm', 10, 3);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (14, 'Sàn gỗ Kahn siêu chịu nước', 213750.00, 1331231.00, 'Sàn gỗ Kahn siêu chịu nước', 3, 2);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (15, 'Sàn gỗ Inovar Merbau', 209000.00, 418000.00, 'Sàn gỗ Inovar Merbau', 2, 3);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (16, 'Vòi Sen 2 Chân 1 Cán', 997500.00, 1234.00, 'Vòi Sen 2 Chân 1 Cán', 5, 4);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (17, 'Vòi rửa bát nòng lạnh Gorlde', 1691000.00, 3382000.00, 'Vòi rửa bát nòng lạnh Gorlde', 10, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (18, 'Vòi rửa bát lạnh Selta', 418000.00, 836000.00, 'Vòi rửa bát lạnh Selta', 9, 4);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (19, 'Vòi lavabo lạnh bán tự động', 1311000.00, 2622000.00, 'Vòi lavabo lạnh bán tự động', 6, 2);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (20, 'Vòi rửa bát nóng lạnh Selta', 845500.00, 1691000.00, 'Vòi rửa bát nóng lạnh Selta', 5, 3);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (21, 'Quần áo bảo vệ', 26030.00, 52060.00, 'Quần áo bảo vệ', 7, 4);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (22, 'Áo phản quang lưới Việt Nam', 20330.00, 40660.00, 'Áo phản quang lưới Việt Nam', 8, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (23, 'Bịt tai chống ồn', 4940.00, 9880.00, 'Bịt tai chống ồn', 4, 3);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (24, 'Nút tai 3M không dây', 4465.00, 8930.00, 'Nút tai 3M không dây', 10, 1);
insert into product(id, product_name, capital_price, sale_price, product_description, category_id, unit_id) values (25, 'Quần áo Bảo hộ lao động vải chéo xanh', 53580.00, 107160.00, 'Quần áo Bảo hộ lao động vải chéo xanh', 8, 4);

INSERT INTO category (category_id, category_name) VALUES (1, '本');

INSERT INTO goods (goods_id, goods_name, description, category_id, price)
VALUES ('366cf3a4-68c5-4dae-a557-673769f76840', 'こころ', '夏目 漱石の本です', 1, 900);
INSERT INTO goods (goods_id, goods_name, description, category_id, price)
VALUES ('366cf3a4-68c5-4dae-a557-673769f76841', '〔雨ニモマケズ〕', '宮沢 賢治の本です', 1, 800);
INSERT INTO goods (goods_id, goods_name, description, category_id, price)
VALUES ('366cf3a4-68c5-4dae-a557-673769f76842', '走れメロス', '太宰 治の本です', 1, 880);
INSERT INTO goods (goods_id, goods_name, description, category_id, price)
VALUES ('366cf3a4-68c5-4dae-a557-673769f76843', '吾輩は猫である', '夏目 漱石の本です', 1, 900);
INSERT INTO goods (goods_id, goods_name, description, category_id, price)
VALUES ('366cf3a4-68c5-4dae-a557-673769f76844', '人間失格', '太宰 治の本です', 1, 880);

INSERT INTO account (email, PASSWORD, name, birth_day, ZIP, address)
VALUES ('demo1@example.com', 'demo', '山田太郎', '2000-01-01', '1000000', '東京都');
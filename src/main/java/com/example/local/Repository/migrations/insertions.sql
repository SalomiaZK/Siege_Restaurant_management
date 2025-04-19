INSERT INTO best_sales (best_sales_id, best_sales_date) VALUES
                                                            ('BS001', '2023-01-15 12:00:00'),
                                                            ('BS002', '2023-02-20 12:00:00'),
                                                            ('BS003', '2023-03-10 12:00:00'),
                                                            ('BS004', '2023-04-05 12:00:00'),
                                                            ('BS005', '2023-05-12 12:00:00');

INSERT INTO sales_element (sales_element_id, sale_point, sold_quantity, total_amount, best_sales_id) VALUES
                                                                                                         ('SE001', 'Point de vente principal', 150, 4500.50, 'BS001'),
                                                                                                         ('SE002', 'Kiosque centre-ville', 85, 2550.75, 'BS001'),
                                                                                                         ('SE003', 'Service traiteur', 200, 6000.00, 'BS002'),
                                                                                                         ('SE004', 'Livraison à domicile', 120, 3600.25, 'BS003'),
                                                                                                         ('SE005', 'Point de vente secondaire', 90, 2700.50, 'BS004');


INSERT INTO dish (dish_id, dish_name, dish_price) VALUES
                                                      ('D001', 'Poulet rôti aux herbes', 1500),
                                                      ('D002', 'Salade César', 800),
                                                      ('D003', 'Pâtes carbonara', 1200),
                                                      ('D004', 'Tiramisu', 600),
                                                      ('D005', 'Soupe du jour', 500),
                                                      ('D006', 'Steak frites', 1800),
                                                      ('D007', 'Tarte aux pommes', 700);

INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_modification_date, ingredient_unity) VALUES
                                                                                                            ('I001', 'Poulet', '2023-01-01 00:00:00', 'U'),
                                                                                                            ('I002', 'Laitue', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I003', 'Crème fraîche', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I004', 'Pâtes', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I005', 'Lardons', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I006', 'Oeufs', '2023-01-01 00:00:00', 'U'),
                                                                                                            ('I007', 'Café', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I008', 'Biscuits cuillère', '2023-01-01 00:00:00', 'U'),
                                                                                                            ('I009', 'Mascarpone', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I010', 'Steak haché', '2023-01-01 00:00:00', 'U'),
                                                                                                            ('I011', 'Pommes de terre', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I012', 'Pommes', '2023-01-01 00:00:00', 'U'),
                                                                                                            ('I013', 'Pâte brisée', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I014', 'Herbes de Provence', '2023-01-01 00:00:00', 'G'),
                                                                                                            ('I015', 'Carottes', '2023-01-01 00:00:00', 'G');


INSERT INTO ing_price (price_id, price_date, unit_price, ingredient_id) VALUES
                                                                            ('IP001', '2023-01-01 00:00:00', 500, 'I001'),
                                                                            ('IP002', '2023-01-01 00:00:00', 2, 'I002'),
                                                                            ('IP003', '2023-01-01 00:00:00', 10, 'I003'),
                                                                            ('IP004', '2023-01-01 00:00:00', 3, 'I004'),
                                                                            ('IP005', '2023-01-01 00:00:00', 15, 'I005'),
                                                                            ('IP006', '2023-01-01 00:00:00', 20, 'I006'),
                                                                            ('IP007', '2023-01-01 00:00:00', 30, 'I007'),
                                                                            ('IP008', '2023-01-01 00:00:00', 5, 'I008'),
                                                                            ('IP009', '2023-01-01 00:00:00', 25, 'I009'),
                                                                            ('IP010', '2023-01-01 00:00:00', 400, 'I010'),
                                                                            ('IP011', '2023-01-01 00:00:00', 2, 'I011'),
                                                                            ('IP012', '2023-01-01 00:00:00', 1, 'I012'),
                                                                            ('IP013', '2023-01-01 00:00:00', 8, 'I013'),
                                                                            ('IP014', '2023-01-01 00:00:00', 15, 'I014'),
                                                                            ('IP015', '2023-01-01 00:00:00', 1, 'I015'),
                                                                            ('IP016', '2023-03-01 00:00:00', 550, 'I001'), -- Mise à jour de prix
                                                                            ('IP017', '2023-03-01 00:00:00', 3, 'I002'); -- Mise à jour de prix


INSERT INTO dish_to_ingredient (ingredient_id, dish_id, ingredient_quantity) VALUES
                                                                                 ('I001', 'D001', 1), -- 1 poulet pour le poulet rôti
                                                                                 ('I014', 'D001', 20), -- 20g d'herbes
                                                                                 ('I002', 'D002', 100), -- 100g de laitue
                                                                                 ('I003', 'D002', 50), -- 50g de crème
                                                                                 ('I006', 'D002', 1), -- 1 oeuf
                                                                                 ('I004', 'D003', 200), -- 200g de pâtes
                                                                                 ('I005', 'D003', 100), -- 100g de lardons
                                                                                 ('I006', 'D003', 2), -- 2 oeufs
                                                                                 ('I003', 'D003', 50), -- 50g de crème
                                                                                 ('I007', 'D004', 10), -- 10g de café
                                                                                 ('I008', 'D004', 2), -- 2 biscuits
                                                                                 ('I009', 'D004', 100), -- 100g mascarpone
                                                                                 ('I006', 'D004', 3), -- 3 oeufs
                                                                                 ('I010', 'D006', 1), -- 1 steak
                                                                                 ('I011', 'D006', 300), -- 300g pommes de terre
                                                                                 ('I012', 'D007', 3), -- 3 pommes
                                                                                 ('I013', 'D007', 200); -- 200g pâte


INSERT INTO transaction (transaction_id, transaction_date, transaction_type, transaction_used_quantity, transaction_unity, ingredient_id) VALUES
                                                                                                                                              ('T001', '2023-01-02 09:00:00', 'ADDITION', 50, 'U', 'I001'),
                                                                                                                                              ('T002', '2023-01-02 09:00:00', 'ADDITION', 10000, 'G', 'I002'),
                                                                                                                                              ('T003', '2023-01-03 10:00:00', 'SUBSTRACTION', 2, 'U', 'I001'),
                                                                                                                                              ('T004', '2023-01-03 10:00:00', 'SUBSTRACTION', 500, 'G', 'I002'),
                                                                                                                                              ('T005', '2023-01-04 11:00:00', 'ADDITION', 100, 'U', 'I006'),
                                                                                                                                              ('T006', '2023-01-05 09:00:00', 'SUBSTRACTION', 10, 'U', 'I006'),
                                                                                                                                              ('T007', '2023-01-06 10:00:00', 'ADDITION', 20000, 'G', 'I004'),
                                                                                                                                              ('T008', '2023-01-07 11:00:00', 'SUBSTRACTION', 1000, 'G', 'I004');


INSERT INTO "order" (order_id, order_date) VALUES
                                               ('ORD001', '2023-01-10 12:30:00'),
                                               ('ORD002', '2023-01-10 12:35:00'),
                                               ('ORD003', '2023-01-10 13:00:00'),
                                               ('ORD004', '2023-01-11 19:30:00'),
                                               ('ORD005', '2023-01-12 20:15:00');


INSERT INTO order_to_dish (ordered_id, order_id, dish_id, order_quantity) VALUES
                                                                              ('OD001', 'ORD001', 'D001', 2),
                                                                              ('OD002', 'ORD001', 'D004', 1),
                                                                              ('OD003', 'ORD002', 'D003', 1),
                                                                              ('OD004', 'ORD002', 'D002', 1),
                                                                              ('OD005', 'ORD003', 'D006', 3),
                                                                              ('OD006', 'ORD003', 'D007', 2),
                                                                              ('OD007', 'ORD004', 'D005', 4),
                                                                              ('OD008', 'ORD005', 'D001', 1),
                                                                              ('OD009', 'ORD005', 'D003', 1),
                                                                              ('OD010', 'ORD005', 'D004', 2);INSERT INTO status (status_begining_date, status, ordered_id) VALUES
                                                                                                                                                                               ('2023-01-10 12:31:00', 1, 'OD001'),
                                                                                                                                                                               ('2023-01-10 12:32:00', 2, 'OD001'),
 INSERT INTO status (status_begining_date, status, ordered_id) VALUES
('2023-01-10 12:45:00', 4, 'OD001');                                                                                                                                            ('2023-01-10 12:40:00', 3, 'OD001'),
                                                                                                                                                                               ('2023-01-10 12:36:00', 1, 'OD002'),
                                                                                                                                                                               ('2023-01-10 12:37:00', 2, 'OD002'),
                                                                                                                                                                               ('2023-01-10 13:01:00', 1, 'OD003'),
                                                                                                                                                                               ('2023-01-10 13:05:00', 2, 'OD003'),
                                                                                                                                                                               ('2023-01-10 13:10:00', 3, 'OD003'),
                                                                                                                                                                               ('2023-01-11 19:31:00', 1, 'OD004'),
                                                                                                                                                                               ('2023-01-11 19:35:00', 2, 'OD004'),
                                                                                                                                                                               ('2023-01-12 20:16:00', 1, 'OD005');



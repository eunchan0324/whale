-- 메뉴 테스트 데이터
INSERT INTO menus (id, name, price, category, description)
VALUES (RANDOM_UUID(), '아메리카노', 4500, 'COFFEE', '깊고 진한 에스프레소에 물을 더한 커피'),
       (RANDOM_UUID(), '카페라떼', 5000, 'COFFEE', '에스프레소와 스팀 우유의 조화'),
       (RANDOM_UUID(), '카푸치노', 5000, 'COFFEE', '에스프레소와 우유 거품의 완벽한 균형'),
       (RANDOM_UUID(), '바닐라라떼', 5500, 'COFFEE', '달콤한 바닐라 시럽이 들어간 라떼'),
       (RANDOM_UUID(), '카라멜마끼아또', 5500, 'COFFEE', '카라멜 시럽과 우유, 에스프레소'),
       (RANDOM_UUID(), '녹차라떼', 5500, 'BEVERAGE', '고소한 녹차와 우유'),
       (RANDOM_UUID(), '초코라떼', 5500, 'BEVERAGE', '진한 초콜릿과 우유'),
       (RANDOM_UUID(), '딸기스무디', 6000, 'BEVERAGE', '신선한 딸기로 만든 스무디'),
       (RANDOM_UUID(), '치즈케이크', 6000, 'DESSERT', '부드러운 크림치즈 케이크'),
       (RANDOM_UUID(), '티라미수', 6500, 'DESSERT', '이탈리아 전통 디저트');
CREATE TABLE IF NOT EXISTS member(
    member_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR (255),
    name VARCHAR (255) NOT NULL,
    role ENUM ('ADMIN', 'USER') NOT NULL,
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (member_id),
    UNIQUE KEY (email, name)
);

CREATE TABLE IF NOT EXISTS category(
    category_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id),
    UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS electronic_device(
    electronic_device_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (electronic_device_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS evaluation_item(
    evaluation_item_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    electronic_device_id BIGINT UNSIGNED NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (evaluation_item_id),
    FOREIGN KEY (electronic_device_id)
    REFERENCES electronic_device(electronic_device_id)
);

CREATE TABLE IF NOT EXISTS evaluation(
    evaluation_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    evaluation_item_id BIGINT UNSIGNED NOT NULL,
    evaluation_score INT NOT NULL CHECK ( evaluation_score >= 1 AND evaluation.evaluation_score <= 5 ),
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (evaluation_id),
    FOREIGN KEY (evaluation_item_id)
    REFERENCES evaluation_item(evaluation_item_id)
);

CREATE TABLE IF NOT EXISTS board(
    board_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    member_id BIGINT UNSIGNED NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    view BIGINT UNSIGNED NOT NULL DEFAULT 0,
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    electronic_device_id BIGINT UNSIGNED,
    PRIMARY KEY (board_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id),
    FOREIGN KEY (electronic_device_id)
    REFERENCES electronic_device(electronic_device_id)
);

CREATE TABLE IF NOT EXISTS board_comment(
    board_comment_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    member_id BIGINT UNSIGNED NOT NULL,
    board_id BIGINT UNSIGNED NOT NULL,
    comment VARCHAR(255) NOT NULL,
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (board_comment_id),
    FOREIGN KEY (board_id) REFERENCES board(board_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

CREATE TABLE IF NOT EXISTS image(
    image_id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    origin_name VARCHAR(255) NOT NULL,
    store_name VARCHAR(255) NOT NULL,
    created_time DATETIME(6) NOT NULL,
    updated_time DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    electronic_device_id BIGINT UNSIGNED,
    PRIMARY KEY (image_id),
    FOREIGN KEY (electronic_device_id)
    REFERENCES electronic_device(electronic_device_id),
    UNIQUE KEY (store_name)
);
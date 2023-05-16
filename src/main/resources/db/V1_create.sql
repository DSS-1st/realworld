CREATE TABLE `users`
(
    `user_id`    bigint NOT NULL AUTO_INCREMENT,
    `email`      varchar(255) DEFAULT NULL,
    `password`   varchar(255) DEFAULT NULL,
    `bio`        varchar(255) DEFAULT NULL,
    `image`      varchar(255) DEFAULT NULL,
    `username`   varchar(255) DEFAULT NULL,
    `created_at` datetime     DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `UK_email` (`email`),
    UNIQUE KEY `UK_username` (`username`)
);

CREATE TABLE `article`
(
    `article_id`  bigint       NOT NULL AUTO_INCREMENT,
    `body`        varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    `slug`        varchar(255) DEFAULT NULL,
    `title`       varchar(255) NOT NULL,
    `user_id`     bigint       DEFAULT NULL,
    `created_at`  datetime     DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  datetime     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`article_id`)
);

CREATE TABLE 'comments'
(
    `comment_id` bigint       NOT NULL AUTO_INCREMENT,
    `article_id` bigint,
    `body`       varchar(255) NOT NULL,
    `user_id`    bigint,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id)
);

CREATE TABLE `follow_relation`
(
        `followee_id` bigint NOT NULL,
        `follower_id` bigint NOT NULL,
        `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
        `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`followee_id`, `follower_id`)
);
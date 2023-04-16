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
)
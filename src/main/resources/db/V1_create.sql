DROP TABLE IF EXISTS `users`;
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

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `article_id`  bigint       NOT NULL AUTO_INCREMENT,
    `title`       varchar(255) NOT NULL,
    `slug`        varchar(255) DEFAULT NULL,
    `body`        varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    `user_id`     bigint       NOT NULL,
    `created_at`  datetime     DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  datetime     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`article_id`),
    KEY `IX_user_id` (`user_id`),
    CONSTRAINT `FK_article_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`
(
    `tag_id` bigint       NOT NULL AUTO_INCREMENT,
    `name`   varchar(255) NOT NULL,
    PRIMARY KEY (`tag_id`),
    UNIQUE KEY `UK_name` (`name`)
);

DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`
(
    `article_tag_id` bigint NOT NULL AUTO_INCREMENT,
    `article_id`     bigint NOT NULL,
    `tag_id`         bigint NOT NULL,
    PRIMARY KEY (`article_tag_id`),
    KEY `IX_article_id` (`article_id`),
    KEY `IX_tag_id` (`tag_id`),
    CONSTRAINT `FK_article_tag_article_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
    CONSTRAINT `FK_article_tag_tag_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
);

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`
(
    `comment_id` bigint       NOT NULL AUTO_INCREMENT,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `body`       varchar(255) NOT NULL,
    `article_id` bigint       NOT NULL,
    `user_id`    bigint       NOT NULL,
    PRIMARY KEY (`comment_id`),
    KEY `IX_article_id` (`article_id`),
    KEY `IX_user_id` (`user_id`),
    CONSTRAINT `FK_comments_article_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
    CONSTRAINT `FK_comments_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `follow_relation`;
CREATE TABLE `follow_relation`
(
    `from_user_id` bigint NOT NULL,
    `to_user_id`   bigint NOT NULL,
    `created_at`   datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`from_user_id`,to_user_id)
);
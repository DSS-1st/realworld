SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(255) NULL DEFAULT NULL,
    `password` VARCHAR(255) NULL DEFAULT NULL,
    `bio` VARCHAR(255) NULL DEFAULT NULL,
    `image` VARCHAR(255) NULL DEFAULT NULL,
    `username` VARCHAR(255) NULL DEFAULT NULL,
    `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `UK_email` (`email` ASC) VISIBLE,
    UNIQUE INDEX `UK_username` (`username` ASC) VISIBLE
);

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `article_id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `slug` VARCHAR(255) NOT NULL,
    `body` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `user_id` BIGINT NOT NULL,
    `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`article_id`),
    INDEX `IX_user_id` (`user_id` ASC) INVISIBLE,
    CONSTRAINT `FK_article_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`
(
    `tag_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`tag_id`),
    UNIQUE INDEX `UK_name` (`name` ASC) VISIBLE
);

DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`
(
    `article_tag_id` BIGINT NOT NULL AUTO_INCREMENT,
    `article_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,
    `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`article_tag_id`),
    INDEX `IX_article_id` (`article_id` ASC) INVISIBLE,
    INDEX `IX_tag_id` (`tag_id` ASC) INVISIBLE,
    CONSTRAINT `FK_article_tag_article_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
    CONSTRAINT `FK_article_tag_tag_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
);

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`
(
    `comment_id` BIGINT NOT NULL AUTO_INCREMENT,
    `body` VARCHAR(255) NOT NULL,
    `article_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`comment_id`),
    INDEX `IX_article_id` (`article_id` ASC) VISIBLE,
    INDEX `IX_user_id` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FK_comments_article_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
    CONSTRAINT `FK_comments_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `follow_relation`;
CREATE TABLE `follow_relation`
(
    `login_id` BIGINT NOT NULL,
    `target_id` BIGINT NOT NULL,
    `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`login_id`, `target_id`),
    INDEX `IX_login_id_id` (`login_id` ASC) INVISIBLE,
    INDEX `IX_target_id_idx` (`target_id` ASC) INVISIBLE,
    CONSTRAINT `FK_follow_relation_users_login_id` FOREIGN KEY (`login_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FK_follow_relation_users_target_id` FOREIGN KEY (`target_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `article_users`;
CREATE TABLE IF NOT EXISTS `article_users`
(
    `article_users_id` bigint NOT NULL AUTO_INCREMENT,
    `article_id` bigint NOT NULL,
    `favorited_id` bigint NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`article_users_id`),
    UNIQUE KEY `UX_article_id_favorited_id` (`article_id`,`favorited_id`) INVISIBLE,
    KEY `IX_article_id` (`article_id`) INVISIBLE,
    KEY `IX_favorited_id` (`favorited_id`),
    CONSTRAINT `FK_article_users_users_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
    CONSTRAINT `FK_article_users_users_user_id` FOREIGN KEY (`favorited_id`) REFERENCES `users` (`user_id`)
);

SET FOREIGN_KEY_CHECKS = 1;
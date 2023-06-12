insert into users(email, password, username)
values ('jake@jake.jake', '$2a$10$zAxD2GhYio4kp19PxiYAa./VDiyG.XnGato5DgbxOzxBDZvqDYMn6', 'Jacob');

insert into users(email, password, username)
values ('kate@realworld.com', '$2a$10$2uPXKd3/CutJqFyc.Do3LO90oxzOmqh3UFFC5T9T0sdojlVZ1Iyq6', 'Kate');

insert into article(title, slug, body, description, user_id)
values ('new title 1', 'new-title-1', 'body content', 'article description', 1);

insert into article(title, slug, body, description, user_id)
values ('new title 2', 'new-title-2', 'body content', 'article description', 2);

insert into article(title, slug, body, description, user_id)
values ('new title 3', 'new-title-3', 'body content', 'article description', 2);

insert into tag(name)
values ('dvorak');

insert into tag(name)
values ('qwerty');

insert into tag(name)
values ('sebul');

insert into comments(body, article_id, user_id)
values ('His name was my name too.', 1, 1);

insert into following(target_id, login_id)
values (2, 1);

insert into article_tag(article_id, tag_id)
values (1, 1);

insert into article_tag(article_id, tag_id)
values (1, 2);

insert into article_tag(article_id, tag_id)
values (1, 3);

insert into article_users(article_id, favorited_id)
values (3, 1)
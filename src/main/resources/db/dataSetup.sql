insert into users(email, password, username)
values ('jake@jake.jake', 'jakejake', 'Jacob');

insert into users(email, password, username)
values ('kate@realworld.com', 'katekate', 'kate');

insert into article(title, slug, body, description, user_id)
values ('new title 1', 'new-title-1', 'body content', 'article description', 1);

insert into article(title, slug, body, description, user_id)
values ('new title 100', 'new-title-100', 'body content', 'article description', 2);

insert into article(title, slug, body, description, user_id)
values ('new title 101', 'new-title-101', 'body content', 'article description', 2);

insert into tag(name)
values ('dvorak');

insert into comments(body, article_id, user_id)
values ('His name was my name too.', 1, 1);

insert into follow_relation(login_id, target_id)
values (1, 2);

insert into article_tag(article_id, tag_id)
values (1, 1);

insert into article_users(article_id, favorited_id)
values (3, 1)
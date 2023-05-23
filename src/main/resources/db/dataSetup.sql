insert into users(email, password, username)
values ('jake000@jake.jake', 'jakejake', 'Jacob000');

insert into users(email, password, username)
values ('kate@realworld.com', 'katekate', 'kate');

insert into article(title, slug, body, description, user_id)
values ('new title 1', 'new-title-1', 'body content', 'article description', 1);

insert into tag(name)
values ('dvorak');

insert into comments(body, article_id, user_id)
values ('His name was my name too.', 1, 1);

insert into article_tag(article_id, tag_id)
values (1, 1);
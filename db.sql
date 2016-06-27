drop database warmlight;
create database warmlight;
use warmlight;

create table t_user(
 id serial primary key,
user_name varchar(300),
password varchar(300),
user_img varchar(300),
intro text,
real_name varchar(300),
register_date date
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_token(
id serial primary key,
token varchar(300),
user_id bigint(20) unsigned not null,
login_date timestamp,
foreign key(user_id) references t_user(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_news(
 id serial primary key,
user_id bigint(20) unsigned not null,
content text,
publish_date date,
foreign key(user_id) references t_user(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_file(
 id serial primary key,
src varchar(300),
type int,
background_no int,
news_id bigint(20) unsigned not null,
foreign key(news_id) references t_news(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_vote(
id serial primary key,
news_id bigint(20) unsigned not null,
user_id bigint(20) unsigned not null,
voted_date date,
foreign key(news_id) references t_news(id) ON DELETE CASCADE, 
foreign key(user_id) references t_user(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_comment(
id serial primary key,
news_id bigint(20) unsigned not null,
user_id bigint(20) unsigned not null,
publish_date date,
comment text,
foreign key(news_id) references t_news(id) ON DELETE CASCADE,
foreign key(user_id) references t_user(id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_news add column voted_amount bigint;

alter table t_user add column background_img varchar(300);

alter table t_comment add column type int;
alter table t_comment add column voice_src varchar(300);

CREATE TRIGGER `add_voted` AFTER INSERT ON `t_vote`
 FOR EACH ROW update t_news set voted_amount = voted_amount + 1 where t_news.id = new.news_id;
 
 CREATE TRIGGER `delete_vote` AFTER DELETE ON `t_vote`
 FOR EACH ROW update t_news set voted_amount = voted_amount - 1 where t_news.id = old.news_id;
 
create database GKCalendar default character set utf8;
-- 部分字段后期修改
create table user( 
username varchar(20) primary key,
password varchar(20)
) DEFAULT CHARSET=utf8;

create table calendar_member(
calender_id int,
username varchar(20),
id int primary key AUTO_INCREMENT
);

create table calender_detail(
version int,
state int, -- 0 无操作； 1 读； 2 写
calender_id int AUTO_INCREMENT,
calender_title varchar(20),
creator varchar(20),
content text,
effective_date int,
primary key(calender_id,calender_title)
);
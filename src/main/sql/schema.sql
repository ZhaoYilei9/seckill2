CREATE DATABASE seckill;
USE seckill;
CREATE TABLE seckill(
seckill_id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
number INT NOT NULL,
start_time TIMESTAMP NOT NULL #######惨痛错误每次都刷新时间戳,
end_time  TIMESTAMP NOT NULL DEFAULT current_timestamp,
create_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
PRIMARY KEY(seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)engine=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

insert into seckill(name,number,start_time,end_time)
values('1000元秒杀iphone6',100,'2018-04-28 00:00:00','2018-04-30 00:00:00'),
      ('500元秒杀ipad2',100,'2018-04-28 00:00:00','2018-04-30 00:00:00'),
      ('300元秒杀小米2',100,'2018-04-28 00:00:00','2018-04-30 00:00:00'),
      ('100元秒杀红米',100,'2018-04-28 00:00:00','2018-04-30 00:00:00');

CREATE TABLE success_killed(
    seckill_id INT NOT NULL ,
    user_phone bigint NOT NULL,
    status TINYINT NOT NULL DEFAULT -1,
    create_time TIMESTAMP NOT NULL ,
    PRIMARY KEY (seckill_id,user_phone) /*联合主键*/,
    key idx_create_time(create_time)
)engine=InnoDB DEFAULT CHARSET=utf8;




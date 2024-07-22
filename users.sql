use todo;
-- users 테이블 있는 경우 삭제.
drop table if exists user;

-- users 테이블 칼럼 생성
create table user (
                       id varchar(12) not null primary key,
                       password varchar(12) not null,
                       name varchar(30) not null,
                       role varchar(6) not null,
                       email varchar(30) not null

);

-- users 테이블 실제 데이터 입력
insert into user (id, password, name, role, email)
values('guest', 'guest123', '방문자', 'USER', 'yaya@hoho.com');

insert into user (id, password, name, role, email)
values('admin', 'admin123', '관리자', 'ADMIN', 'yaya2@hoho.com');

insert into user (id, password, name, role, email)
values('member', 'member123', '일반회원', 'USER', 'yaya3@hoho.com');

-- 전체 데이터 출력
select * from user;
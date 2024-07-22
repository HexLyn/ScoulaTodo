use todo;
-- todo 테이블 있는 경우 삭제.
drop table if exists todo;

-- todo 테이블 칼럼 생성
create table TODO (
                       ID INTEGER AUTO_INCREMENT not null primary key,
                       TITLE varchar(128) not null,
                       DESCRIPTION varchar(512) null,
                       DONE BOOLEAN,
                       USERID varchar(12) not null,
                       FOREIGN KEY (USERID) REFERENCES user(ID)
                        ON UPDATE CASCADE
                        ON DELETE CASCADE

);

-- users 테이블 실제 데이터 입력
insert into todo (title, description, done, userid)
values('야구장', '경기보는거 중요하지', false, 'guest');

insert into todo (title, description, done, userid)
values('농구장', '경기보는거 중요하지2', false, 'guest');

insert into todo (title, description, done, userid)
values('배구장', '경기보는거 중요하지3', false, 'member');

insert into todo (title, description, done, userid)
values('공부 쪼끔', '해야할까', true, 'guest');

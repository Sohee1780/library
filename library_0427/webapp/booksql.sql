select * from (
    select rownum rn, t.* from(
    select no, title, nvl((select 대여여부 
                        from rent where bookno = no and 대여여부='Y'),'N') rentyn, author 
    from book
    where title like '%서울%'
)t )where rn between 1 and 10;


select * from (
    select rownum rn, t.* from (
        select b.no, b.title, b.rentyn, b.author, to_char(rentday, 'yy/mm/dd') 대여일, to_char(RETERNABLEDAY, 'yy/mm/dd') 반납가능일, sfile, ofile
            from book b, (
            select * from rent where 대여여부='Y'
            )d 
        where b.no = d.bookno(+)
        order by no desc
    ) t
) where rn between 1 and 10;


select * from (
    select rownum rn, t.* from (
        select b.no, b.title, b.rentyn, b.author, d.id, to_char(rentday, 'yy/mm/dd') 대여일, to_char(RETERNABLEDAY, 'yy/mm/dd') 반납가능일, reternday, sfile, ofile, d.rentno
        from book b, rent d
        where b.no = d.bookno(+) AND title like '%수요일%'
        order by no desc
    ) t
) where rn between 1 and 10;

-- 대여 가능한지 조회
SELECT * FROM book WHERE no=165 AND (rentno IS NULL OR rentno='');

-- 대여번호
SELECT 'R'||LPAD(seq_rent.nextval,5,0) FROM dual;

-- 대여 가능한지 조회해서 업데이트
UPDATE book 
SET rentno='R00022' 
WHERE no=165 AND (rentno IS NULL OR rentno='');

-- 대여 테이블 인서트
INSERT INTO rent VALUES('R00022', '아이디', 165, 'Y', SYSDATE, null, SYSDATE+14, null);

-- 대여중인 책 리스트
select * from rent where id = 'RURU' AND 대여여부='Y';

-- 대여 기록 리스트 
select * from rent where id = 'RURU';
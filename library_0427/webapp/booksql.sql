select * from (
    select rownum rn, t.* from(
    select no, title, nvl((select 대여여부 
                        from rent where bookno = no and 대여여부='Y'),'N') rentyn, author 
    from book
    where title like '%서울%'
)t )where rn between 1 and 10;

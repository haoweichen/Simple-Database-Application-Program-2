with month as(
select case
when month = 1 then '1'
when month = 2 then '2'
when month = 3 then '3'
when month = 4 then '4'
when month = 5 then '5'
when month = 6 then '6'
when month = 7 then '7'
when month = 8 then '8'
when month = 9 then '9'
when month = 10 then '10'
when month = 11 then '11'
when month = 12 then '12' end mon
from sales
group by month
),

combi as(
select cust, prod, mon
from sales, month
group by cust, prod, mon
),

m1 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust,prod, sum(quant), count(quant)
from sales
where month =1
group by cust, prod) as s
),

m2 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 2
group by cust, prod) as s
),

m3 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust,prod, sum(quant), count(quant)
from sales
where month = 3
group by cust, prod) as s
),

m4 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 4
group by cust, prod) as s
),

m5 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 5
group by cust, prod) as s
),

m6 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 6
group by cust, prod) as s
),

m7 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 7
group by cust, prod) as s
),

m8 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 8
group by cust, prod) as s
),

m9 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 9
group by cust, prod) as s
),

m10 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 10
group by cust, prod) as s
),

m11 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 11
group by cust, prod) as s
),

m12 as(
select cust, prod, round(sum/count) as avg
from
(select 
cust, prod, sum(quant), count(quant)
from sales
where month = 12
group by cust, prod) as s
),

report2 as(
select combi.cust, combi.prod, combi.mon,
case 
when combi.mon='1' then NULL 
when combi.mon='2' then m1.avg 
when combi.mon='3' then m2.avg 
when combi.mon='4' then m3.avg
when combi.mon='5' then m4.avg
when combi.mon='6' then m5.avg
when combi.mon='7' then m6.avg
when combi.mon='8' then m7.avg
when combi.mon='9' then m8.avg
when combi.mon='10' then m9.avg
when combi.mon='11' then m10.avg
when combi.mon='12' then m11.avg end Before_Avg,

case 
when combi.mon='1' then m2.avg 
when combi.mon='2' then m3.avg 
when combi.mon='3' then m4.avg 
when combi.mon='4' then m5.avg
when combi.mon='5' then m6.avg
when combi.mon='6' then m7.avg
when combi.mon='7' then m8.avg
when combi.mon='8' then m9.avg
when combi.mon='9' then m10.avg
when combi.mon='10' then m11.avg
when combi.mon='11' then m12.avg
when combi.mon='12' then NULL end After_Avg
from combi

left outer join m1 on combi.cust=m1.cust and combi.prod=m1.prod
left outer join m2 on combi.cust=m2.cust and combi.prod=m2.prod
left outer join m3 on combi.cust=m3.cust and combi.prod=m3.prod
left outer join m4 on combi.cust=m4.cust and combi.prod=m4.prod
left outer join m5 on combi.cust=m5.cust and combi.prod=m5.prod
left outer join m6 on combi.cust=m6.cust and combi.prod=m6.prod
left outer join m7 on combi.cust=m7.cust and combi.prod=m7.prod
left outer join m8 on combi.cust=m8.cust and combi.prod=m8.prod
left outer join m9 on combi.cust=m9.cust and combi.prod=m9.prod
left outer join m10 on combi.cust=m10.cust and combi.prod=m10.prod
left outer join m11 on combi.cust=m11.cust and combi.prod=m11.prod
left outer join m12 on combi.cust=m12.cust and combi.prod=m12.prod
)

select * from report2
order by mon
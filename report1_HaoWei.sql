select cust, prod, round(avg(quant)) as the_avg, round((
select avg(quant)
from sales s
where s.prod!=sales.prod and s.cust=sales.cust)) as OTHER_PROD_AVG, round((
select avg(quant)
from sales s_2
where s_2.prod=sales.prod and s_2.cust!=sales.cust)) as OTHER_CUST_AVG 
from sales 
group by cust, prod
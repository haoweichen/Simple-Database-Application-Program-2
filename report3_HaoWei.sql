with stat as(
	select prod, cust, month, avg(quant) as theavg, max(quant) as themax
	from sales
	group by prod, cust, month
)

select distinct s.prod, s.cust, s.month, befores.before, afters.after
from sales s

left outer join

(select s.prod, s.cust, s.month-1 as month, count(*) as after
from sales s
where exists 
(	
	select * from stat 
	where s.prod = stat.prod and s.cust = stat.cust and s.month = stat.month+1 
		and s.quant between stat.theavg and stat.themax
)
group by s.prod, s.cust, s.month
) afters

on s.prod = afters.prod and s.cust = afters.cust and s.month = afters.month

left outer join

(select s.prod, s.cust, s.month+1 as month, count(*) as before
from sales s
where exists 
(	
	select * from stat 
	where s.prod = stat.prod and s.cust = stat.cust and s.month = stat.month-1 
		and s.quant between stat.theavg and stat.themax
)
group by s.prod, s.cust, s.month
) befores
on

s.prod = befores.prod and s.cust = befores.cust and s.month = befores.month

where befores.before is not null or afters.after is not null

order by month
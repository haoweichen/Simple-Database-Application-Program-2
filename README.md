# Simple-Database-Application-Program-2

• Generate reports based on the following queries:
  1. For each customer and product, compute (1) the customer's average sale of this product, (2) the customer’s average sale of the other products (3) the average sale of the product for the other customers and.
  2. For customer and product, show the average sales before and after each month (e.g., for February, show average sales of January and March. For “before” January and “after” December, display <NULL>). The “YEAR” attribute is not considered for this query – for example, both January of 1997 and January of 1998 are considered January regardless of the year.
  3. For customer and product, count for each month, how many sales of the previous and how many sales of the following month had quantities between that month’s average sale and maximum sale. Again for this query, the “YEAR” attribute is not considered.
For this assignment, you can write either 3 separate programs, one for each of the 3 reports, or one program generating all of the 3 reports.
Again, the only SQL statement you’re allowed to use for your program is:
select * from sales;
That is, no where clauses, no aggregate functions (e.g., avg, sum, count), etc.
And, you cannot store the ‘sales’ table in memory.
The following are sample report output (NOTE: the numbers shown below are not the actual aggregate values. You can write simple SQL queries to find the actual aggregate values).

[image](https://github.com/haoweichen/Simple-Database-Application-Program-2/blob/master/DB_sample2.png)

/*
 * 문제 1번 :  각 배우의 총 출연 횟수와 순위를 계산하라.
 * */


SELECT 
    a.actor_id,
    a.first_name,
    a.last_name,
    COUNT(fa.film_id) AS total_film,
    rank() OVER (ORDER BY COUNT(fa.film_id) DESC) AS rank_num
FROM 
    actor a
JOIN 
    film_actor fa ON a.actor_id = fa.actor_id
GROUP BY 
    a.actor_id
ORDER BY 
    rank_num;


/*
 * 문제 2: 각 영화의 총 대여 횟수와 해당 영화의 순위 계산 - 동순위 처리 필요
 * */

SELECT 
    f.film_id, 
    f.title, 
    COUNT(r.rental_id) AS rental_count,
    RANK() OVER (ORDER BY COUNT(r.rental_id) DESC) AS rank_num
FROM
	rental r
	JOIN inventory i ON r.inventory_id = i.inventory_id
	JOIN film f ON i.film_id = f.film_id
GROUP BY
	f.film_id
ORDER BY
	rank_num;


/*
 * 문제 3: 각 고객의 대여 횟수와 고객의 순위 계산
 */

SELECT 
    c.customer_id,
    concat(c.first_name," ", c.last_name) as customer_name, 
    COUNT(r.rental_id) AS rental_count,
    DENSE_RANK() OVER (ORDER BY COUNT(r.rental_id) DESC) AS rank_num
FROM 
    customer c
LEFT JOIN 
    rental r ON c.customer_id = r.customer_id
GROUP BY 
    c.customer_id
ORDER BY 
    rank_num;

/*
 * 문제 4: 대여가 가장 많은 직원과 그 순위 계산
 * */
SELECT 
    s.staff_id,
    concat(s.first_name," ", s.last_name) as staff_name,
    COUNT(r.rental_id) AS rental_count,
    ROW_NUMBER() OVER (ORDER BY COUNT(r.rental_id) DESC) AS rank_num
FROM 
    staff s
LEFT JOIN 
    rental r ON s.staff_id = r.staff_id
GROUP BY 
    s.staff_id
ORDER BY 
    rental_count DESC;

/*
 * 문제 5: 각 카테고리에서 가장 많이 대여된 영화의 순위
 */
with cte
as
(
SELECT 
	c.category_id ,
	c.name ,
	f.film_id ,
	f.title ,
	count(f.film_id) as total_rentals 
from
	rental r 
join 
	inventory i on i.inventory_id = r.inventory_id 
join
	film f on f.film_id = i.film_id 
join
	film_category fc on fc.film_id = f.film_id 
join
	category c on c.category_id = fc.category_id 
group by 
	c.name , c.category_id , f.film_id , f.title
)
select 
	cte.category_id ,
	cte.name ,
	cte.film_id ,
	cte.title ,
	cte.total_rentals ,
	row_number() over(partition by cte.category_id order by cte.total_rentals desc) as rank_num
from
	cte
order by
	cte.category_id
;



/*
 * 문제 6: 각 배우가 출연한 영화의 평균 대여 횟수와 순위
 * */
WITH cte
as (
SELECT
	fa.actor_id,
    f.film_id,
    COUNT(r.rental_id) AS rental_count
FROM
    film f 
    inner join film_actor fa ON fa.film_id = f.film_id
    inner join inventory i ON f.film_id = i.film_id
    inner join rental r ON i.inventory_id = r.inventory_id
GROUP BY
	fa.actor_id,
    f.film_id
)
select
	a.actor_id,
    a.first_name,
    a.last_name,
	AVG(rental_count) AS avg_film_rentals,
	RANK() OVER (ORDER BY AVG(rental_count) DESC) AS rank_num
FROM 
	cte
	inner join actor a on a.actor_id= cte.actor_id
group by
	cte.actor_id   
;

/*
 * 문제 7: 각 고객의 평균 대여 기간과 순위 계산
 * */
SELECT 
    c.customer_id, 
    concat(c.first_name, " ",c.last_name), 
    AVG(DATEDIFF(r.return_date, r.rental_date)) AS avg_rental_period,
    RANK() OVER (ORDER BY AVG(DATEDIFF(r.return_date, r.rental_date)) DESC) AS rank_num
FROM 
    customer c
    inner JOIN rental r ON c.customer_id = r.customer_id
GROUP BY 
    c.customer_id
ORDER BY 
    rank_num;

/*
 * 문제 8: 각 직원이 처리한 대여 건수의 누적 합계
 */
SELECT 
    s.staff_id, 
    CONCAT(s.first_name, ' ', s.last_name) AS staff_name,
    r.rental_date,
    COUNT(*) OVER (PARTITION BY s.staff_id ORDER BY r.rental_date) as cumulative_rentals
FROM staff s
	inner join rental r ON s.staff_id = r.staff_id
GROUP BY s.staff_id, r.rental_date
ORDER BY s.staff_id;

/*
 * 문제 9: 각 영화의 평균 대여 기간과 순위 계산
 */
select
	x.film_id,
    x.title,
    x.avg_rental_duration,
    rank() over( order by avg_rental_duration desc) as rank_num
from
	(select
		f.film_id,
		f.title,  
		avg(datediff(r.return_date, r.rental_date)) as avg_rental_duration
	from
			film f
	join inventory i on f.film_id = i.film_id
	join rental r on i.inventory_id = r.inventory_id
	group by f.film_id
	) as x
;



/*
 * 문제 10: 각 카테고리별 평균 대여 횟수의 순위 계산
 * */
WITH cte AS (
    SELECT 
        c.category_id,
        c.name AS category_name,
        COUNT(r.rental_id) / NULLIF(COUNT(DISTINCT f.film_id),0) AS avg_rentals
    FROM 
        category c
        JOIN film_category fc ON c.category_id = fc.category_id
        JOIN film f ON fc.film_id = f.film_id
        JOIN inventory i ON f.film_id = i.film_id
        JOIN rental r ON i.inventory_id = r.inventory_id
    where f.film_id is not null
	GROUP BY 
        c.category_id, c.name
	having
		COUNT(DISTINCT f.film_id) >0
)
SELECT 
    category_id,
    category_name,
    avg_rentals,
    RANK() OVER (ORDER BY avg_rentals DESC) AS rank_num
FROM 
    cte
ORDER BY 
    rank_num;

/*
 * 문제 11: 대여 횟수가 가장 많은 고객을 나라별로 구분하여 순위 계산
 */
select
	c.country,
	c3.customer_id,
	concat(c3.first_name," ", c3.last_name) as customer_name,
	count(c3.customer_id ) as total_rentals
	,rank() over(partition by c.country order by count(c3.customer_id )desc) as rank_num
from
	country c
	inner join city c2 on c2.country_id = c.country_id
	inner join address a on a.city_id = c2.city_id
	inner join customer c3 on c3.address_id = a.address_id 
	inner join rental r on r.customer_id = c3.customer_id
group by
	c3.customer_id, c.country

	
/*
 * 문제 12: 특정 날짜 범위에서 각 카테고리의 누적 대여 횟수 계산
 * 특정 날짜 '2005-07-01' ~ '2005-07-31'
 * */
SELECT 
    c.category_id
    , c.name as category_name
    , r.rental_date
    , COUNT(*) OVER (PARTITION BY c.category_id ORDER BY r.rental_date) as cumulative_rentals
FROM 
    rental r
    inner join inventory i ON r.inventory_id = i.inventory_id
    inner join film f ON i.film_id = f.film_id
    inner join film_category fc on fc.film_id = f.film_id
    inner join category c ON fc.category_id = c.category_id
WHERE 
    r.rental_date BETWEEN '2005-07-01' AND '2005-07-31'

/*
 * 문제 13: 가장 많이 대여된 영화 TOP 5의 대여 횟수와 순위
 * */
SELECT
	f.film_id
	, f.title AS title
	, COUNT(r.rental_id) AS total_rentals
	, rank() over(order by COUNT(r.rental_id) desc) as rank_num
FROM
	rental r
	inner join inventory i ON r.inventory_id = i.inventory_id
	inner join film f ON i.film_id = f.film_id
GROUP BY f.film_id, f.title
LIMIT 5;

/*
 * 문제 14: 대여된 영화의 평균 수익과 순위 계산
 */
SELECT
	f.film_id
	, f.title AS title
	, avg(p.amount) as avg_revenue
	, rank() over(order by avg(p.amount) desc) as rank_num
FROM
	rental r
	inner join inventory i ON r.inventory_id = i.inventory_id
	inner join film f ON i.film_id = f.film_id
	inner join payment p on p.rental_id =r.rental_id
GROUP BY f.film_id, f.title;


/*
 * 
 * 문제 15: 특정 배우(PENELOPE GUINESS)가 출연한 영화 중 가장 높은 수익을 낸 영화 순위 조회
 */
SELECT
	a.actor_id
	, concat(a.first_name," ", a.last_name)
	, f.film_id
	, f.title AS title
	, sum(p.amount) as avg_revenue
	, rank() over(order by sum(p.amount) desc) as rank_num
FROM
	rental r
	inner join inventory i ON r.inventory_id = i.inventory_id
	inner join film f ON i.film_id = f.film_id
	inner join payment p on p.rental_id =r.rental_id
	inner join film_actor fa on fa.film_id=f.film_id 
	inner join actor a on a.actor_id = fa.actor_id
GROUP BY a.actor_id,f.film_id, f.title , a.first_name, a.last_name
having a.first_name ='Penelope' and a.last_name = 'guiness';

/*
 * 문제 16: 대여 금액이 가장 높은 고객 TOP 10 계산
 * */
select
	c.customer_id
	, concat(c.first_name," ", c.last_name) as customer_name
	, sum(p.amount) as total_amount
	, rank() over(order by sum(p.amount) desc) as rank_num
from
	customer c
	inner join rental r on c.customer_id  = r.customer_id 
	inner join payment p on p.rental_id =r.rental_id
group by c.customer_id

/*
 * 문제 17: 매월 총 수익 계산과 월별 순위
 * */
select 
	date_format(p.payment_date, '%Y-%m') as payment_month
	, sum(p.amount) as total_revenue
	, rank() over(order by sum(p.amount) desc) as rank_num 
from 
	payment p
group by payment_month;

/*
 * 문제 18: 각 배우별 평균 수익과 순위 계산 
 * 상위 5명 조회
 * */
select
	a.actor_id,
	concat(a.first_name," ",a.last_name),
	avg(p.amount) as avg_revenue,
	rank() over(order by avg(p.amount) desc) as rank_num 
from actor a
	inner join film_actor fa on fa.actor_id = a.actor_id
	inner join film f on f.film_id = fa.film_id
	inner JOIN inventory i on i.film_id = f.film_id
	inner join rental r on r.inventory_id = i.inventory_id
	inner join payment p on p.rental_id = r.rental_id
group by
	a.actor_id
limit 5
	;

/*
 * 문제 19: 각 지역별 총 대여 수익과 순위
 * 상위 5개 조회
 * */
select 
	c.country,
	sum(p.amount) as total_revenue,
	rank() over(order by sum(p.amount) desc) as rank_num
from country c
	inner join city c2 on c2.country_id = c.country_id
	inner join address a on c2.city_id = a.city_id
	inner join customer c3 on a.address_id =c3.address_id 
	inner join rental r on r.customer_id = c3.customer_id 
	inner join payment p on p.rental_id = r.rental_id 
group by c.country
limit 5;

/*
 * 문제 20: 대여 횟수가 가장 많은 배우 10명의 출연 영화 횟수
 */
with temp(actor_id, actor_name, rental_count) as  (
SELECT a.actor_id
     , concat(a.first_name,' ', a.last_name) as actor_name
     , count(r.rental_id) as rental_count
  from actor a 
  join film_actor fa on a.actor_id = fa.actor_id 
  join film f on fa.film_id = f.film_id 
  join inventory i on f.film_id = i.film_id 
  join rental r on i.inventory_id = r.inventory_id 
group by a.actor_id 
order by rental_count desc
limit 10
)
select t.actor_id
	 , t.actor_name
	 , count(f2.film_id) as total_films
	 , ROW_NUMBER () over(order by count(f2.film_id) desc) as rank_num
  from temp t
  join film_actor fa2 on t.actor_id = fa2.actor_id 
  join film f2 on fa2.film_id = f2.film_id 
group by t.actor_id
;


/*
 * 문제 21. 고객별 결제 금액 추세 비교 (이전/다음 결제 금액으로부터 증감 계산)
 * - 각 고객별로 결제 건수를 결제일 기준 오름차순으로 나열했을 때,
 * - 직전 결제 금액과 다음 결제 금액을 비교하여 현재 결제 금액이 전/후 대비 어떻게 달라졌는지 계산 조회회.
 */
SELECT
    p.customer_id,
    p.payment_date,
    p.amount AS current_amount,
    LAG(p.amount) OVER (PARTITION BY p.customer_id ORDER BY p.payment_date) AS prev_amount,
    LEAD(p.amount) OVER (PARTITION BY p.customer_id ORDER BY p.payment_date) AS next_amount,
    (p.amount - LAG(p.amount) OVER (PARTITION BY p.customer_id ORDER BY p.payment_date)) AS diff_with_prev,
    (LEAD(p.amount) OVER (PARTITION BY p.customer_id ORDER BY p.payment_date) - p.amount) AS diff_with_next
FROM
    payment p
ORDER BY
    p.customer_id,
    p.payment_date;

commit;
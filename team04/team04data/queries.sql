1. SELECT week, COUNT(orderID) AS total_orders FROM orders GROUP BY week;
-- '''"52 weeks of sales history": select the count of orders grouped by week -- e.g. "week 1 has 123 orders"''' -- selects the week column from the restaurant_orders table and calculates the count of distinct orderID values for each week. --gets it from the orders table` --group each count by a week 
2. SELECT hour, COUNT(orderID) AS total_orders, SUM(sale) AS total_sales FROM orders GROUP BY hour;
-- '''"Realistic sales history": select count of orders, sum of order total grouped by hour -- e.g. "12pm has 12345 orders totaling $86753"''' --count of the total orders based on their distinct ids and save it under total_orders for each hours --get from the orders table --add up the total sales for each hours --group it by hours 
3. SELECT day, month, year, SUM(sale) as total_sale FROM orders GROUP BY day, month, year ORDER BY total_sale DESC LIMIT 10;
-- '''"2 peak days": select top 10 sums of order total grouped by day in descending order by order total -- e.g. "30 August has $123456 of sales"'''

4. SELECT menu_item, COUNT(*) AS ingredients_count FROM ingredients GROUP BY menu_item ORDER BY menu_item LIMIT 20;
-- '''"Inventory items for 20 menu items": select count of inventory items from inventory and menu grouped by menu item -- e.g. "chicken fingers uses 12 items"''' -- Limiting the output to the top 20 menu items with the highest inventory items count -- Counting the occurrences of each menu item in the Inventory table -- Joining Ingredients and Inventory tables on menu_item -- Grouping the results by menu_item -- Ordering the results by inventory_items_count in descending order

5. SELECT hour, COUNT(*) AS total_orders FROM orders GROUP BY hour ORDER BY total_orders DESC LIMIT 10;
-- '''"Top 10 Busiest Hours of the Day": select count of orders grouped by hour of the day -- e.g. "The top selling hour was 2pm"'''
--this selects the hour column from the orders table --the COUNT() function calculates the total rows for each hour and assigns it to total_orders --gets those values from the orders table --groups the total_orders by each hour --order table results in descending order -- Limiting the output to the top 10

6. WITH ranked_items AS ( SELECT day, month, year, menu_item, COUNT(menu_item) AS itemsSold, RANK() OVER (PARTITION BY day, month, year ORDER BY COUNT(menu_item) DESC) FROM orders GROUP BY day, month, year, menu_item) SELECT * FROM ranked_items;
 -- Highest sold item of each day over 52 weeks SELECT day, month, year, menu_item, itemsSold FROM ranked_items WHERE rank = 1 ORDER BY year, month, day;

7. WITH ranked_items AS ( SELECT month, year, menu_item, COUNT(menu_item) AS itemsSold, RANK() OVER (PARTITION BY month, year ORDER BY COUNT(menu_item) DESC) AS rank FROM orders GROUP BY month, year, menu_item) SELECT month, year, menu_item, itemsSold FROM ranked_items WHERE rank = 1 ORDER BY year, month;
-- '''"Highest sold item of the month"'''

8. SELECT month, year, SUM(sale) as totalSale FROM orders GROUP BY month, year ORDER BY totalSale DESC LIMIT 1;
-- '''"Most profitable month"''' -- This selects the month and year calculates the sum of sales for each month and year, labeling this sum as totalSale -- ensures that sales are aggregated for each specific month within each year

9. SELECT orderID, SUM(sale) AS total_sale FROM orders WHERE day=1 AND month=5 AND year=2023 GROUP BY orderID ORDER BY total_sale DESC LIMIT 5;
-- '''"Highest Valued Order Sales on January 5th": select top five sum of sales grouped by orderID on January 5th -- e.g. "The second largest sale total was $15.00"'''

10. SELECT ingredient, (count * (SELECT COUNT(*) FROM orders WHERE menu_item = 'Double Stack Cheese Burger')) AS total_ingredients FROM menu WHERE menu_item = 'Double Stack Cheese Burger' GROUP BY ingredient, count;
-- '''"Total Ingredient Items for a Specific Menu Item": show quantity used of each ingredient of one menu item over the whole year -- e.g. "Revs Burger requires Burger Bread, a Patty, ...."'''

11. SELECT ingredient, CAST((CAST(amount AS decimal(5,2)) / capacity) AS decimal(5,2)) as ratio FROM inventory ORDER BY ratio ASC LIMIT 5;
---Lowest Inventory Item ratios

12. SELECT ingredient, SUM(count) as total_count FROM menu GROUP BY ingredient HAVING SUM(count)>1 ORDER BY total_count DESC;
-- Most used Ingredients over all menu items out of ingredients that are used more than once

13. SELECT menu.menu_item, menu.ingredient, menu.count, inventory.amount, inventory.capacity FROM inventory INNER JOIN menu ON inventory.ingredient=menu.ingredient;
--Joint table with menu ingredients having amount and capacity added

14. SELECT day, SUM(sale) AS total_sale FROM orders GROUP BY day ORDER BY total_sale DESC LIMIT 10;

15. SELECT ingredient, (count * (SELECT COUNT(*) FROM ingredients WHERE menu_item = 'Double Stack Cheese Burger')) AS total_ingredients FROM ingredients WHERE menu_item = 'Double Stack Cheese Burger' GROUP BY ingredient, count;
-- Total ingredient items for a Double Stack Cheese Burger --

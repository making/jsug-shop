SELECT
  g.goods_id      AS goods_id,
  g.goods_name    AS goods_name,
  g.description   AS description,
  g.price         AS price,
  c.category_id   AS category_id,
  c.category_name AS category_name
FROM goods AS g, category AS c
WHERE
  g.goods_id = :goodsId
  AND
  g.category_id = c.category_id
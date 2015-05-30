SELECT COUNT(*)
FROM goods AS g, category AS c
WHERE
  c.category_id = :categoryId
  AND
  g.category_id = c.category_id

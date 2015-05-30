SELECT
  email,
  password,
  name,
  birth_day,
  zip,
  address
FROM account
WHERE email = :email
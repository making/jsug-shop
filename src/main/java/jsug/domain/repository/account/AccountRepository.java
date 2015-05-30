package jsug.domain.repository.account;

import jsug.domain.model.Account;
import jsug.domain.repository.SqlFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Repository
@Transactional
public class AccountRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    SqlFinder sql;

    public int countByEmail(String email) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("email", email);
        return jdbcTemplate.queryForObject(sql.get("sql/account/countByEmail.sql"),
                source, Integer.class);
    }

    public Optional<Account> findOne(String email) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("email", email);
        return jdbcTemplate.query(sql.get("sql/account/findOne.sql"), source,
                (rs, i) -> Account.builder()
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .birthDay(rs.getDate("birth_day").toLocalDate())
                        .address(rs.getString("address"))
                        .zip(rs.getString("zip"))
                        .build())
                .stream()
                .findFirst();
    }

    public Account create(Account account) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("email", account.getEmail())
                .addValue("password", account.getPassword())
                .addValue("name", account.getName())
                .addValue("birthDay", Date.valueOf(account.getBirthDay()))
                .addValue("zip", account.getZip())
                .addValue("address", account.getAddress());
        jdbcTemplate.update(sql.get("sql/account/create.sql"), source);
        return account;
    }
}

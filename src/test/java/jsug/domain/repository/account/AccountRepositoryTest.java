package jsug.domain.repository.account;

import jsug.domain.model.Account;
import jsug.domain.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = {
        "/sql/drop-tables.sql",
        "/db/migration/V1__create-schema.sql",
        "/sql/insert-accounts.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testCreateAndFind() throws Exception {
        Account account = Account.builder()
                .email("foo@example.com")
                .password("aaaa")
                .name("Taro Yamada")
                .birthDay(LocalDate.of(2000, 1, 1))
                .zip("1000000")
                .address("Tokyo")
                .build();
        Account created = accountRepository.create(account);
        Account found = accountRepository.findOne("foo@example.com").get();
        assertThat(found, is(created));
    }

    @Test
    public void testCountByEmail() throws Exception {
        long count = accountRepository.countByEmail("demo1@example.com");
        assertThat(count, is(1L));
    }

    @Test
    public void testCountByEmail_NotFound() throws Exception {
        long count = accountRepository.countByEmail("hoge@example.com");
        assertThat(count, is(0L));
    }

    @Test
    public void testFindOne() throws Exception {
        Optional<Account> opt = accountRepository.findOne("demo1@example.com");
        assertThat(opt.isPresent(), is(true));
        Account account = opt.get();
        assertThat(account.getName(), is("山田太郎"));
        assertThat(account.getEmail(), is("demo1@example.com"));
        assertThat(account.getPassword(), is("demo"));
        assertThat(account.getBirthDay(), is(LocalDate.of(2000, 1, 1)));
        assertThat(account.getZip(), is("1000000"));
        assertThat(account.getAddress(), is("東京都"));
    }


    @Test
    public void testFindOne_NotFound() throws Exception {
        Optional<Account> opt = accountRepository.findOne("hoge@example.com");
        assertThat(opt.isPresent(), is(false));
    }
}
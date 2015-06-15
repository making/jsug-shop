package jsug.domain.service.account;

import jsug.domain.TestConfig;
import jsug.domain.model.Account;
import jsug.domain.repository.account.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = {
        "/sql/drop-tables.sql",
        "/db/migration/V1__create-schema.sql",
        "/sql/insert-accounts.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testIsUnusedEmail_Used() throws Exception {
        assertThat(accountService.isUnusedEmail("demo1@example.com"), is(false));
    }

    @Test
    public void testIsUnusedEmail_Unused() throws Exception {
        assertThat(accountService.isUnusedEmail("hoge@example.com"), is(true));
    }

    @Test
    public void testRegister() throws Exception {
        Account account = Account.builder()
                .email("foo@example.com")
                .name("Taro Yamada")
                .birthDay(LocalDate.of(2000, 1, 1))
                .zip("1000000")
                .address("Tokyo")
                .build();
        Account created = accountService.register(account, "password");
        assertThat(created.getPassword(), is("password"));
        Account found = accountRepository.findOne("foo@example.com").get();
        assertThat(found, is(created));
    }
}
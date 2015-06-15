package jsug.domain.service.userdetails;

import jsug.domain.TestConfig;
import jsug.domain.model.Account;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class ShopUserDetailsServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    UserDetailsService userDetailsService;

    @Test
    public void testLoadUserByUsername() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername("demo1@example.com");
        assertThat(userDetails.getUsername(), is("demo1@example.com"));
        assertThat(userDetails.getPassword(), is("demo"));
        assertThat(userDetails.getAuthorities(), is(AuthorityUtils.createAuthorityList("DEMO")));
        assertThat(userDetails, is(instanceOf(ShopUserDetails.class)));
        Account account = ShopUserDetails.class.cast(userDetails).getAccount();
        assertThat(account.getName(), is("山田太郎"));
        assertThat(account.getEmail(), is("demo1@example.com"));
        assertThat(account.getPassword(), is("demo"));
        assertThat(account.getBirthDay(), is(LocalDate.of(2000, 1, 1)));
        assertThat(account.getZip(), is("1000000"));
        assertThat(account.getAddress(), is("東京都"));
    }

    @Test
    public void testLoadUserByUsername_NotFound() throws Exception {
        expectedException.expect(UsernameNotFoundException.class);
        userDetailsService.loadUserByUsername("hoge@example.com");
    }
}
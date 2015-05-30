package jsug.domain.service.userdetails;

import jsug.domain.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findOne(username)
                .map(ShopUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " is not found!"));
    }
}

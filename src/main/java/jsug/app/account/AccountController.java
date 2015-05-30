package jsug.app.account;

import jsug.domain.model.Account;
import jsug.domain.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @ModelAttribute
    AccountForm setupForm() {
        return new AccountForm();
    }

    @RequestMapping(value = "create", params = "form", method = RequestMethod.GET)
    String createForm() {
        return "account/createForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    String create(@Validated AccountForm form, BindingResult bindingResult,
                  RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "account/createForm";
        }
        Account account = Account.builder()
                .name(form.getName())
                .email(form.getEmail())
                .birthDay(form.getBirthDay())
                .zip(form.getZip())
                .address(form.getAddress())
                .build();
        accountService.register(account, form.getPassword());
        attributes.addFlashAttribute(account);
        return "redirect:/account/create?finish";
    }

    @RequestMapping(value = "create", params = "finish", method = RequestMethod.GET)
    String createFinish() {
        return "account/createFinish";
    }
}

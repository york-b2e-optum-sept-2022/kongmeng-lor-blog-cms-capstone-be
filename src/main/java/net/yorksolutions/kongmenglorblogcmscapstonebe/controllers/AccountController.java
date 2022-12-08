package net.yorksolutions.kongmenglorblogcmscapstonebe.controllers;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.CreateAccountDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.SendMessageDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.MessageEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountController {
    AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping
    public AccountEntity create(@RequestBody CreateAccountDTO dto) {
        return this.accountService.create(dto);
    }
    @GetMapping("/getAll")
    public Iterable<AccountEntity> getAll() {
        return this.accountService.getAll();
    }
    @GetMapping("/login")
    public Optional<AccountEntity> login(@RequestParam String email, @RequestParam String password) {
        return this.accountService.login(email,password);
    }
    @PostMapping("/sendMessage")
    public MessageEntity createMessage(@RequestBody SendMessageDTO dto) {
        return this.accountService.createMessage(dto);
    }
    @PostMapping("/post/blog")
    public BlogEntity postBlog(@RequestBody BlogDTO dto) {
        return this.accountService.postBlog(dto);
    }
}

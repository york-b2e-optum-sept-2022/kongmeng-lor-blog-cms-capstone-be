package net.yorksolutions.kongmenglorblogcmscapstonebe.controllers;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.CreateAccountDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.LoginDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.services.AccountService;
import org.apache.juli.logging.Log;
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
    public Optional<AccountEntity> login(@RequestParam LoginDTO dto) {
        return this.accountService.login(dto);
    }
}

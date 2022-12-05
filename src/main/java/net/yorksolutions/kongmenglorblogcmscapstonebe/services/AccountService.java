package net.yorksolutions.kongmenglorblogcmscapstonebe.services;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.CreateAccountDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.LoginDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.AccountRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepositories accountRepositories;

    public AccountService(AccountRepositories accountRepositories) {
        this.accountRepositories = accountRepositories;
    }

    public AccountEntity create(CreateAccountDTO dto) {
        if (dto.email == "") {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (dto.password == "") {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (dto.name == "") {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!dto.password.equals(dto.confirm_Password)) {
            //if password does not match.
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        System.out.println(dto.email);
        System.out.println(dto.password);
        System.out.println(dto.name);
        return this.accountRepositories.save(new AccountEntity(dto.email,dto.password,dto.name));
    }

    public Iterable<AccountEntity> getAll() {
        return this.accountRepositories.findAll();
    }

    public Optional<AccountEntity> login(LoginDTO dto) {
        if (dto.email == "") {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (dto.password == "") {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional account = this.accountRepositories.findByEmailAndPassword(dto.email, dto.password);
        if (account!=null) {
            return account;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

package net.yorksolutions.kongmenglorblogcmscapstonebe.services;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.CreateAccountDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.LoginDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.SendMessageDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.MessageEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.AccountRepositories;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.MessageRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class AccountService {
    AccountRepositories accountRepositories;
    MessageRepositories messageRepositories;


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
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Optional acc = this.accountRepositories.findByEmail(dto.email);
        if (acc.isEmpty()) {
            return this.accountRepositories.save(new AccountEntity(dto.email,dto.password,dto.name));
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    }

    public Iterable<AccountEntity> getAll() {
        return this.accountRepositories.findAll();
    }

    public Optional<AccountEntity> login(String email, String password) {
        if (email == "") {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (password == "") {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional account = this.accountRepositories.findByEmailAndPassword(email,password);
        if (account!=null) {
            return account;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public MessageEntity createMessage(SendMessageDTO dto) {
        Optional<AccountEntity> current_Account = this.accountRepositories.findById(dto.current_Id);
        Optional<AccountEntity> send_Account = this.accountRepositories.findById(dto.sender_Id);
        if (send_Account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (current_Account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (current_Account.get().getSent() == false) {
            //First time sending message
            Map<Long, String> history = new HashMap<Long, String>();
            history.put(dto.sender_Id,dto.message);

            Map<Long, String> history1 = new HashMap<Long, String>();
            history1.put(dto.current_Id,dto.message);


            List<MessageEntity> messageEntities = new ArrayList<MessageEntity>();
            List<MessageEntity> messageEntities1 = new ArrayList<MessageEntity>();

            MessageEntity messageEntity = new MessageEntity(dto.message,history,null);

            MessageEntity messageEntity1 = new MessageEntity(dto.message, null,history1);

            messageEntities.add(messageEntity);
            messageEntities1.add(messageEntity1);
            current_Account.get().setSent(true);
            send_Account.get().setSent(true);

            send_Account.get().setMessageEntity(messageEntities1);
            current_Account.get().setMessageEntity(messageEntities);
            this.accountRepositories.save(send_Account.get());
            this.accountRepositories.save(current_Account.get());
            return messageEntity;
        }

        return null;
    }
}

package net.yorksolutions.kongmenglorblogcmscapstonebe.services;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.CreateAccountDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.SendMessageDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.MessageEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.AccountRepositories;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.MessageRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class AccountService {
    AccountRepositories accountRepositories;
    MessageRepositories messageRepositories;

    public AccountService(AccountRepositories accountRepositories, MessageRepositories messageRepositories) {
        this.accountRepositories = accountRepositories;
        this.messageRepositories = messageRepositories;
    }

    public AccountEntity create(CreateAccountDTO dto) {
        if (dto.email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (dto.password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (dto.name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!dto.password.equals(dto.confirm_Password)) {
            //if password does not match.
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Optional<AccountEntity> acc = this.accountRepositories.findByEmail(dto.email);
        if (acc.isEmpty()) {
            return this.accountRepositories.save(new AccountEntity(dto.email,dto.password,dto.name,new ArrayList<>(), new ArrayList<>()));
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    }
    public Iterable<AccountEntity> getAll() {
        return this.accountRepositories.findAll();
    }
    public Optional<AccountEntity> login(String email, String password) {
        if (email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<AccountEntity> account = this.accountRepositories.findByEmailAndPassword(email,password);
        if (account.isPresent()) {
            return account;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
//    SendMessageDTO contains
//    ------------------------
//    public Long current_Id;
//    public String message;
//    public Long sender_Id;
    public MessageEntity createMessage(SendMessageDTO dto) {
        Optional<AccountEntity> account = this.accountRepositories.findById(dto.current_Id);
        Optional<AccountEntity> account2 = this.accountRepositories.findById(dto.sender_Id);

        if (account.isEmpty() || account2.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        AccountEntity current_Account = account.get();
        AccountEntity second_Account = account2.get();

        List<MessageEntity> messageEntityList = new ArrayList<>();
        List<HashMap> history = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();

        if (!current_Account.getMessageCreated()&& !second_Account.getMessageCreated()) {
            hashMap.put(current_Account.getEmail(), dto.message);
            history.add(hashMap);
            MessageEntity messageEntity = new MessageEntity(dto.message,current_Account.getEmail(),second_Account.getEmail(), history);
            messageEntityList.add(messageEntity);

            current_Account.setMessageEntities(messageEntityList);
            second_Account.setMessageEntities(messageEntityList);

            this.accountRepositories.save(current_Account);
            this.accountRepositories.save(second_Account);
            return messageEntity;
        }

        if (current_Account.getMessageCreated() && !second_Account.getMessageCreated()) {
            List<MessageEntity> temp = current_Account.getMessageEntities();
            List<MessageEntity> messageEntities = this.createMessageHelper1(current_Account,second_Account,dto);
            temp.addAll(messageEntities);
            current_Account.setMessageEntities(temp);

            this.accountRepositories.save(current_Account);

            return messageEntities.get(0);
        }
        if (!current_Account.getMessageCreated() && second_Account.getMessageCreated()) {
            List<MessageEntity> temp = second_Account.getMessageEntities();
            List<MessageEntity> messageEntities = this.createMessageHelper2(current_Account,second_Account,dto);
            temp.addAll(messageEntities);

            second_Account.setMessageEntities(temp);
            this.accountRepositories.save(second_Account);
            return messageEntities.get(0);
        }

        long message_Id = -1;
        for (int i = 0; i < current_Account.getMessageEntities().size(); i++) {
            for(int j = 0; j < second_Account.getMessageEntities().size(); j++) {
                if (current_Account.getMessageEntities().get(i).getId() == second_Account.getMessageEntities().get(j).getId()) {
                    message_Id = current_Account.getMessageEntities().get(i).getId();
                }
            }
        }
        return replying(current_Account,second_Account,message_Id, dto.message);
    }
    public MessageEntity replying(AccountEntity current_Account, AccountEntity second_Account, Long message_Id, String messages) {
        Optional<MessageEntity> message = this.messageRepositories.findById(message_Id);
        if (message.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        message.get().setCurrent_Message(messages);
        List<HashMap> temp = message.get().getHistory_Messages();
        HashMap<String , String > hashMap = new HashMap<>();
        hashMap.put(current_Account.getEmail(), messages);
        temp.add(hashMap);
        this.messageRepositories.save(message.get());
        return message.get();
    }
    public List<MessageEntity> createMessageHelper1(AccountEntity current_Account, AccountEntity second_Account, SendMessageDTO dto) {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        List<HashMap> history = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(current_Account.getEmail(), dto.message);
        history.add(hashMap);

        MessageEntity messageEntity = new MessageEntity(dto.message,current_Account.getEmail(),second_Account.getEmail(), history);

        messageEntityList.add(messageEntity);

        second_Account.setMessageCreated(true);
        second_Account.setMessageEntities(messageEntityList);
        this.accountRepositories.save(second_Account);
        return second_Account.getMessageEntities();
    }
    public List<MessageEntity> createMessageHelper2(AccountEntity current_Account, AccountEntity second_Account, SendMessageDTO dto) {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        List<HashMap> history = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(current_Account.getEmail(), dto.message);
        history.add(hashMap);

        MessageEntity messageEntity = new MessageEntity(dto.message,current_Account.getEmail(),second_Account.getEmail(), history);

        messageEntityList.add(messageEntity);

        current_Account.setMessageCreated(true);
        current_Account.setMessageEntities(messageEntityList);
        this.accountRepositories.save(current_Account);
        return current_Account.getMessageEntities();
    }


    public BlogEntity postBlog(BlogDTO dto) {
        Optional<AccountEntity> account = this.accountRepositories.findById(dto.owner_Id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        BlogEntity blogEntity = new BlogEntity(dto.title,dto.body,dto.create_Date,dto.update_Date,dto.owner_Email,dto.owner_Id);
        List<BlogEntity> blogEntities = new ArrayList<>();
        if (account.get().getBlogEntities().isEmpty()) {
            blogEntities.add(blogEntity);
            account.get().setBlogEntities(blogEntities);
            this.accountRepositories.save(account.get());
            return account.get().getBlogEntities().get(0);
        }
        blogEntities = account.get().getBlogEntities();
        blogEntities.add(blogEntity);
        account.get().setBlogEntities(blogEntities);
        this.accountRepositories.save(account.get());
        return account.get().getBlogEntities().get(account.get().getBlogEntities().size()-1);
    }
    public List<BlogEntity> deleteBlog(Long Id) {
        Optional<AccountEntity> account = this.accountRepositories.findById(Id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return account.get().getBlogEntities();
    }


    public List<BlogEntity> getBlogs(Long Id) {
        Optional<AccountEntity> account = this.accountRepositories.findById(Id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return account.get().getBlogEntities();
    }
}

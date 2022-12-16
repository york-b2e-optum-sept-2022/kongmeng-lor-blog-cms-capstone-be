package net.yorksolutions.kongmenglorblogcmscapstonebe.services;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.CreateAccountDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.SendMessageDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.HistoryEntity;
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
            return this.accountRepositories.save(new AccountEntity(dto.email,dto.password,dto.name,new ArrayList<>()));
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
    public MessageEntity createMessage(SendMessageDTO dto) {
        Optional<AccountEntity> account = this.accountRepositories.findById(dto.current_Id);
        Optional<AccountEntity> account2 = this.accountRepositories.findById(dto.sender_Id);

        if (account.isEmpty() || account2.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        AccountEntity current_Account = account.get();
        AccountEntity second_Account = account2.get();


        List<MessageEntity> messageLists = new ArrayList<>();
        List<HistoryEntity> historyLists = new ArrayList<>();

        int page = -1;
        if (!current_Account.getMessageCreated()&& !second_Account.getMessageCreated()) {
            HistoryEntity history = new HistoryEntity(dto.message,current_Account.getEmail());
            historyLists.add(history);
            MessageEntity messageEntity = new MessageEntity(dto.message,current_Account.getEmail(),second_Account.getEmail(), historyLists);
            messageLists.add(messageEntity);


            current_Account.setMessageEntities(messageLists);
            second_Account.setMessageEntities(messageLists);

            this.accountRepositories.save(current_Account);
            this.accountRepositories.save(second_Account);
            return current_Account.getMessageEntities().get(0);
        }
        if (current_Account.getMessageCreated() && !second_Account.getMessageCreated()) {
            page = 1;
            return this.helper2(current_Account,this.helper(current_Account,second_Account,dto,page));
        }
        if (!current_Account.getMessageCreated() && second_Account.getMessageCreated()) {
            return this.helper2(second_Account,this.helper(current_Account,second_Account,dto,page));
        }

        long message_Id = -1;
        for (int i = 0; i < current_Account.getMessageEntities().size(); i++) {
            for(int j = 0; j < second_Account.getMessageEntities().size(); j++) {
                if (current_Account.getMessageEntities().get(i).getId() == second_Account.getMessageEntities().get(j).getId()) {
                    message_Id = current_Account.getMessageEntities().get(i).getId();
                }
            }
        }

        if(message_Id == -1) {
            List<MessageEntity> temp = current_Account.getMessageEntities();
            int index = current_Account.getMessageEntities().size();
            MessageEntity temp2 = this.createMessageHelper3(current_Account,second_Account,dto);
            temp.add(temp2);
            current_Account.setMessageEntities(temp);
            this.accountRepositories.save(second_Account);
            return current_Account.getMessageEntities().get(index);
        }

        return replying(current_Account,second_Account,message_Id, dto.message);

    }
    public MessageEntity replying(AccountEntity current_Account, AccountEntity second_Account, Long message_Id, String messages) {
        Optional<MessageEntity> message = this.messageRepositories.findById(message_Id);
        if (message.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        message.get().setCurrent_Message(messages);
        System.out.println(current_Account.getEmail());
        System.out.println(second_Account.getEmail());
        message.get().setEmail_From(current_Account.getEmail());
        message.get().setEmail_To(second_Account.getEmail());
        List<HistoryEntity> historyEntityList = message.get().getHistoryEntities();
        HistoryEntity history = new HistoryEntity(messages, current_Account.getEmail());
        historyEntityList.add(history);
        this.messageRepositories.save(message.get());
        return message.get();
    }

    public MessageEntity createMessageHelper3(AccountEntity current_Account, AccountEntity second_Account, SendMessageDTO dto) {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        List<HistoryEntity> historyLists = new ArrayList<>();
        HistoryEntity history = new HistoryEntity(dto.message, current_Account.getEmail());
        historyLists.add(history);

        MessageEntity messageEntity = new MessageEntity(dto.message,current_Account.getEmail(),second_Account.getEmail(), historyLists);
        messageEntityList.add(messageEntity);
        List<MessageEntity> temp_Lists = second_Account.getMessageEntities();
        int index = temp_Lists.size();
        temp_Lists.addAll(messageEntityList);
        second_Account.setMessageEntities(temp_Lists);
        this.accountRepositories.save(second_Account);
        System.out.println(second_Account.getMessageEntities().get(index).getId());
        return second_Account.getMessageEntities().get(index);
    }

    public MessageEntity helper2(AccountEntity account, List<MessageEntity> messageEntities) {
        List<MessageEntity> temp = account.getMessageEntities();
        List<MessageEntity> messageLists = messageEntities;

        temp.addAll(messageLists);

        account.setMessageEntities(temp);
        this.accountRepositories.save(account);
        System.out.println(messageEntities.size());
        return messageLists.get(0);
    }






    public List<MessageEntity> helper(AccountEntity current_Account, AccountEntity second_Account, SendMessageDTO dto, int page) {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        List<HistoryEntity> historyLists = new ArrayList<>();
        if (page == 1) {
            HistoryEntity history = new HistoryEntity(dto.message,current_Account.getEmail());
            historyLists.add(history);
            MessageEntity messageEntity = new MessageEntity(dto.message,current_Account.getEmail(),second_Account.getEmail(), historyLists);
            messageEntityList.add(messageEntity);
            second_Account.setMessageEntities(messageEntityList);
            this.accountRepositories.save(second_Account);
            return second_Account.getMessageEntities();
        }
        System.out.println(page);
        HistoryEntity history = new HistoryEntity(dto.message,second_Account.getEmail());
        historyLists.add(history);
        MessageEntity messageEntity = new MessageEntity(dto.message,current_Account.getEmail(),second_Account.getEmail(), historyLists);
        messageEntity.setHistoryEntities(historyLists);
        messageEntityList.add(messageEntity);
        current_Account.setMessageEntities(messageEntityList);
        this.accountRepositories.save(current_Account);
        System.out.println(current_Account.getMessageEntities());
        return current_Account.getMessageEntities();
    }

    public BlogEntity postBlog(BlogDTO dto) {
        Optional<AccountEntity> account = this.accountRepositories.findById(dto.owner_Id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        BlogEntity blogEntity = new BlogEntity(dto.title,dto.body,dto.owner_Email,dto.owner_Id);
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
    public List<MessageEntity> getMessages(Long id) {
        Optional<AccountEntity> account = this.accountRepositories.findById(id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<MessageEntity> messages = account.get().getMessageEntities();
        return messages;
    }

    public List<BlogEntity> getBlogsById(Long id) {
        Optional<AccountEntity> account = this.accountRepositories.findById(id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return account.get().getBlogEntities();
    }
}

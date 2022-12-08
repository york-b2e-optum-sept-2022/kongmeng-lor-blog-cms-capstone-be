package net.yorksolutions.kongmenglorblogcmscapstonebe.services;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.CreateAccountDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.SendMessageDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.Message;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.AccountRepositories;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.MessageRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class AccountService {
    AccountRepositories accountRepositories;
//    MessageRepositories messageRepositories;
    MessageRepositories messageRepositories;

    public AccountService(AccountRepositories accountRepositories, MessageRepositories messageRepositories) {
        this.accountRepositories = accountRepositories;
        this.messageRepositories = messageRepositories;
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
            return this.accountRepositories.save(new AccountEntity(dto.email,dto.password,dto.name,null));
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

    public String createMessage(SendMessageDTO dto) {
        Optional<AccountEntity> current_Account = this.accountRepositories.findById(dto.current_Id);
        Optional<AccountEntity> send_Account = this.accountRepositories.findById(dto.sender_Id);
        if (send_Account.isEmpty() || current_Account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // Never message each other before.
        if (current_Account.get().getMessages().isEmpty() && send_Account.get().getMessages().isEmpty()) {
            List<Message> messageList = new ArrayList<Message>();



            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(current_Account.get().getEmail(), dto.message);
            List<HashMap> newList = new ArrayList<HashMap>();
            newList.add(hashMap);
            Message message = new Message(current_Account.get().getEmail(), send_Account.get().getEmail(),send_Account.get().getId(), dto.message, newList);
            messageList.add(message);




            current_Account.get().setMessages(messageList);
            send_Account.get().setMessages(messageList);
            this.accountRepositories.save(current_Account.get());
            this.accountRepositories.save(send_Account.get());
            return "SUCCESSFUL";
        }
        if (!current_Account.get().getMessages().isEmpty() && send_Account.get().getMessages().isEmpty()) {
           //Current account is not empty
            // but send account is
            List<Message> temp_Account = current_Account.get().getMessages();
            List<Message> messageList = new ArrayList<Message>();

            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(current_Account.get().getEmail(), dto.message);
            List<HashMap> newList = new ArrayList<HashMap>();
            newList.add(hashMap);

            Message message = new Message(current_Account.get().getEmail(), send_Account.get().getEmail(),send_Account.get().getId(), dto.message, newList);





            temp_Account.add(message);
            messageList.add(message);

            current_Account.get().setMessages(temp_Account);
            send_Account.get().setMessages(messageList);
            this.accountRepositories.save(current_Account.get());
            this.accountRepositories.save(send_Account.get());
            return "Successful 2";
        }
        if (current_Account.get().getMessages().isEmpty() && !send_Account.get().getMessages().isEmpty()) {
            List<Message> temp_Account = send_Account.get().getMessages();
            List<Message> messageList = new ArrayList<Message>();


            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(current_Account.get().getEmail(), dto.message);
            List<HashMap> newList = new ArrayList<HashMap>();
            newList.add(hashMap);

            Message message = new Message(current_Account.get().getEmail(), send_Account.get().getEmail(),send_Account.get().getId(), dto.message, newList);
            temp_Account.add(message);
            messageList.add(message);

            current_Account.get().setMessages(temp_Account);
            send_Account.get().setMessages(messageList);
            this.accountRepositories.save(current_Account.get());
            this.accountRepositories.save(send_Account.get());
            return "Successful 3";
        }
        List<Message> temp_Account = current_Account.get().getMessages();
        List<Message> temp_Account2 = send_Account.get().getMessages();



        long index = -1;
        for(int i = 0; i < temp_Account.size(); i++) {
            for (int j = 0; j < temp_Account2.size(); j++) {
                if (temp_Account.get(i).getId() == temp_Account2.get(j).getId()){
                    index = temp_Account.get(i).getId();
                    break;
                }
            }
        }


        System.out.println(index);
        sendMessage(index, dto.message,current_Account.get().getEmail());
        return "GOOD BYE";
    }
    public void sendMessage(Long index, String message, String email) {
        System.out.println("HELLO WORLD");
        Optional<Message> message1 = this.messageRepositories.findById(index);
        if (message1.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        message1.get().setMessage(message);
        HashMap<String, String> send_Message = new HashMap<String, String>();
        send_Message.put(email, message);
        List<HashMap> temp = message1.get().getHistory_Messages();
        temp.add(send_Message);
        message1.get().setHistory_Messages(temp);
        this.messageRepositories.save(message1.get());
    }



}

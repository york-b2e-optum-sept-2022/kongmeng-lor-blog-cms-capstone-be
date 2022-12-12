package net.yorksolutions.kongmenglorblogcmscapstonebe.services;
import net.yorksolutions.kongmenglorblogcmscapstonebe.controllers.MessageController;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.MessageRepositories;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {
    MessageRepositories messageRepositories;
    public MessageService(MessageRepositories messageRepositories) {
        this.messageRepositories = messageRepositories;
    }
}

package net.yorksolutions.kongmenglorblogcmscapstonebe.repositories;

import antlr.debug.MessageEvent;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepositories extends CrudRepository<Message, Long> {
}

package net.yorksolutions.kongmenglorblogcmscapstonebe.repositories;

import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepositories extends CrudRepository<MessageEntity, Long> {
}

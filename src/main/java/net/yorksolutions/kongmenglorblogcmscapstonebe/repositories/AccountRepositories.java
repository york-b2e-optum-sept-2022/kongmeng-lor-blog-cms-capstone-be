package net.yorksolutions.kongmenglorblogcmscapstonebe.repositories;

import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepositories extends CrudRepository<AccountEntity, Long> {


}

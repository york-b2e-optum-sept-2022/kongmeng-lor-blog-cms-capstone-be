package net.yorksolutions.kongmenglorblogcmscapstonebe.repositories;

import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepositories extends CrudRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByEmailAndPassword(String email, String password);
    Optional<AccountEntity> findByEmail(String email);

}

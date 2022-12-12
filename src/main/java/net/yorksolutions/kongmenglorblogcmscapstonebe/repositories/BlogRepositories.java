package net.yorksolutions.kongmenglorblogcmscapstonebe.repositories;

import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepositories extends CrudRepository<BlogEntity,Long> {
}

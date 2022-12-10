package net.yorksolutions.kongmenglorblogcmscapstonebe.repositories;

import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.CommentsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositories extends CrudRepository<CommentsEntity,Long> {
}

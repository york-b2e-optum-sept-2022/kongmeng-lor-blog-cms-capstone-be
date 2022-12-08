package net.yorksolutions.kongmenglorblogcmscapstonebe.services;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogCommentDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogDeleteDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogEditDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.BlogRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    BlogRepositories blogRepositories;

    public BlogService(BlogRepositories blogRepositories) {
        this.blogRepositories = blogRepositories;
    }

    public void deleteBlog(Long Id) {
        this.blogRepositories.deleteById(Id);
    }

    public BlogEntity editBlog(BlogEditDTO dto) {
        BlogEntity blogEntity = checkBlogId(dto.Id);
        if (!dto.title.isEmpty()) {
            blogEntity.setTitle(dto.title);
        }
        if (!dto.body.isEmpty()) {
            blogEntity.setBody(dto.body);
        }
        if (!dto.update_Date.isEmpty()) {
            blogEntity.setUpdate_Date(dto.update_Date);
        }
        this.blogRepositories.save(blogEntity);
        return blogEntity;
    }

    public BlogEntity addComment(BlogCommentDTO dto) {
        BlogEntity blogEntity = checkBlogId(dto.Id);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(dto.user_Id,dto.comments);
        List<HashMap> hashMaps = new ArrayList<>();
        if (blogEntity.getComments().isEmpty()) {
            hashMaps.add(hashMap);
            blogEntity.setComments(hashMaps);
            this.blogRepositories.save(blogEntity);
            return blogEntity;
        }
        hashMaps = blogEntity.getComments();
        hashMaps.add(hashMap);
        blogEntity.setComments(hashMaps);
        this.blogRepositories.save(blogEntity);
        return blogEntity;
    }

    public BlogEntity checkBlogId(Long Id) {
        Optional<BlogEntity> blogEntity = this.blogRepositories.findById(Id);
        if (blogEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return blogEntity.get();
    }

    public BlogEntity deleteComment(BlogDeleteDTO dto) {
        BlogEntity blogEntity = checkBlogId(dto.Id);
        List<HashMap> hashMaps = blogEntity.getComments();
        if (hashMaps.get(dto.index).containsKey(dto.email)) {


            return null;
        }
        return null;
    }
}

package net.yorksolutions.kongmenglorblogcmscapstonebe.services;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.*;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.AccountEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.CommentsEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.AccountRepositories;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.BlogRepositories;
import net.yorksolutions.kongmenglorblogcmscapstonebe.repositories.CommentRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    AccountRepositories accountRepositories;
    BlogRepositories blogRepositories;

    CommentRepositories commentRepositories;

    public BlogService(AccountRepositories accountRepositories, BlogRepositories blogRepositories, CommentRepositories commentRepositories) {
        this.accountRepositories = accountRepositories;
        this.blogRepositories = blogRepositories;
        this.commentRepositories = commentRepositories;
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
        Optional<AccountEntity> account = this.accountRepositories.findById(dto.user_Id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<CommentsEntity> commentsLists;
        CommentsEntity comment = new CommentsEntity(dto.comments,dto.user_Id,account.get().getName(),account.get().getEmail());
        if (blogEntity.getCommentsLists().isEmpty()) {
            commentsLists = new ArrayList<>();
            commentsLists.add(comment);
            blogEntity.setCommentsLists(commentsLists);
            this.blogRepositories.save(blogEntity);
            return blogEntity;
        }
        commentsLists = blogEntity.getCommentsLists();
        commentsLists.add(comment);
        blogEntity.setCommentsLists(commentsLists);
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
    public BlogEntity editComment(BlogAddCommentDTO dto) {
        BlogEntity blog = this.checkBlogId(dto.id);
        blog.getCommentsLists().get(dto.index).setComment(dto.comment);
        return this.blogRepositories.save(blog);
    }
    public BlogEntity deleteComment(BlogDeleteDTO dto) {
        BlogEntity blogEntity = checkBlogId(dto.Id);
        List<CommentsEntity> comments = blogEntity.getCommentsLists();
        List<CommentsEntity> new_Comments = new ArrayList<>();
        if (comments.get(dto.index).getSender().equals(dto.user_Id)) {
            for (int i = 0; i < comments.size(); i++) {
                if (comments.get(i) != comments.get(dto.index)) {
                    new_Comments.add(comments.get(i));
                }
            }
            blogEntity.setCommentsLists(new_Comments);
            this.blogRepositories.save(blogEntity);
            return blogEntity;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public BlogEntity updateViews(UpdateViewsDTO dto) {
        BlogEntity blogEntity = checkBlogId(dto.blogId);
        List<Long> userViews = blogEntity.getView_Accounts();
        Integer temp = blogEntity.getView_Counts();
        if (userViews.isEmpty()) {
            userViews.add(dto.userId);
            blogEntity.setView_Accounts(userViews);
            blogEntity.setView_Counts(1);
            return this.blogRepositories.save(blogEntity);
        }
        for(int i = 0; i < userViews.size(); i++) {
            if (userViews.get(i) == dto.userId) {
                return blogEntity;
            }
        }
        userViews.add(dto.userId);
        temp+=1;
        blogEntity.setView_Accounts(userViews);
        blogEntity.setView_Counts(temp);
        this.blogRepositories.save(blogEntity);
        return this.blogRepositories.save(blogEntity);
    }
    public Iterable<BlogEntity> getAllBlogs() {
        return this.blogRepositories.findAll();
    }

//    public List<BlogEntity> getAllBlogsExceptCurrentId(Long id) {
//        Optional<AccountEntity> account = this.accountRepositories.findById(id);
//        Iterable<BlogEntity> blogs = this.blogRepositories.findAll();
//        if (account.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        blogs.iterator()
//
//    }
}

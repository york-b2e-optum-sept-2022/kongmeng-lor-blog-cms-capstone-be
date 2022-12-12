package net.yorksolutions.kongmenglorblogcmscapstonebe.controllers;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.*;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.services.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/blog")
public class BlogController {
    BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    @PutMapping("/edit")
    public BlogEntity editBlog(@RequestBody BlogEditDTO dto) {
        return this.blogService.editBlog(dto);
    }
    @PostMapping("/add/comment")
    public BlogEntity addComment(@RequestBody BlogCommentDTO dto) {
        return this.blogService.addComment(dto);
    }
    @DeleteMapping("/delete/comment")
    public BlogEntity deleteComment(@RequestBody BlogDeleteDTO dto) {
        return this.blogService.deleteComment(dto);
    }
    @PostMapping("/update/views")
    public BlogEntity updateViews(@RequestBody UpdateViewsDTO dto) {
        return this.blogService.updateViews(dto);
    }
    @GetMapping("/get/allblogs")
    public Iterable<BlogEntity> getAllBlogs() {
        return this.blogService.getAllBlogs();
    }
    @PutMapping("/edit/comment")
    public BlogEntity editComment(@RequestBody BlogAddCommentDTO dto) {
        return this.blogService.editComment(dto);
    }
}

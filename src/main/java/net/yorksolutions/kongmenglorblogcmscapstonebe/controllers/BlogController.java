package net.yorksolutions.kongmenglorblogcmscapstonebe.controllers;

import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogCommentDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogDeleteDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.dto.BlogEditDTO;
import net.yorksolutions.kongmenglorblogcmscapstonebe.entities.BlogEntity;
import net.yorksolutions.kongmenglorblogcmscapstonebe.services.BlogService;
import org.springframework.web.bind.annotation.*;

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
    public void updateViews(@RequestParam Long blogId, @RequestParam Long userId) {
        this.blogService.updateViews(blogId,userId);
    }
}

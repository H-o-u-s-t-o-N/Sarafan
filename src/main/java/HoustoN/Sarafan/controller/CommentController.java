package HoustoN.Sarafan.controller;

import HoustoN.Sarafan.domain.Comment;
import HoustoN.Sarafan.domain.Message;
import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.Views;
import HoustoN.Sarafan.service.CommentService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(
            @RequestBody Comment comment,
            @AuthenticationPrincipal User user
    ) {
        return commentService.create(comment, user);
    }

    @DeleteMapping("{id}")
    public void delete(
            @PathVariable("id") Comment comment,
            @AuthenticationPrincipal User user
    ) {
        commentService.delete(user, comment);
    }
}

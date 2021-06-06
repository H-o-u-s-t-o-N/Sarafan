package HoustoN.Sarafan.service;

import HoustoN.Sarafan.domain.Comment;
import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.Views;
import HoustoN.Sarafan.dto.EventType;
import HoustoN.Sarafan.dto.ObjectType;
import HoustoN.Sarafan.repo.CommentRepo;
import HoustoN.Sarafan.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepo.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}

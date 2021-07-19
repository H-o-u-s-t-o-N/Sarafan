package HoustoN.Sarafan.service;

import HoustoN.Sarafan.domain.*;
import HoustoN.Sarafan.dto.enums.EventType;
import HoustoN.Sarafan.dto.enums.ObjectType;
import HoustoN.Sarafan.repo.CommentRepo;
import HoustoN.Sarafan.repo.UserSubscriptionRepo;
import HoustoN.Sarafan.util.CustomConsumer;
import HoustoN.Sarafan.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final CustomConsumer<User, EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, UserSubscriptionRepo userSubscriptionRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment savedComment = commentRepo.save(comment);

        sendWsMessage(user, EventType.CREATE, savedComment);

        return savedComment;
    }

    public void delete(User user, Comment comment) {
        if(comment.getAuthor().equals(user)){
            commentRepo.delete(comment);

            sendWsMessage(user, EventType.REMOVE, comment);
        }
    }

    private void sendWsMessage(User sender, EventType eventType, Comment comment){
        List<User> recipients = userSubscriptionRepo.findByChannel(sender)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getSubscriber)
                .collect(Collectors.toList());

//        recipients.add(sender);

        recipients.forEach(user -> wsSender.accept(user, eventType, comment));
    }
}

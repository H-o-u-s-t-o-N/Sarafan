package HoustoN.Sarafan.service;

import HoustoN.Sarafan.domain.Message;
import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.UserSubscription;
import HoustoN.Sarafan.domain.Views;
import HoustoN.Sarafan.dto.MessagePageDto;
import HoustoN.Sarafan.dto.MetaDto;
import HoustoN.Sarafan.dto.enums.EventType;
import HoustoN.Sarafan.dto.enums.ObjectType;
import HoustoN.Sarafan.repo.MessageRepo;
import HoustoN.Sarafan.repo.UserSubscriptionRepo;
import HoustoN.Sarafan.util.CustomConsumer;
import HoustoN.Sarafan.util.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";
    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final SimpMessagingTemplate template;

    private final MessageRepo messageRepo;
    private final UserSubscriptionRepo userSubscriptionRepo;
    private final CustomConsumer<User, EventType, Message> wsSender;

    @Autowired
    public MessageService(
            SimpMessagingTemplate template,
            MessageRepo messageRepo,
            UserSubscriptionRepo userSubscriptionRepo,
            WsSender wsSender
    ) {
        this.template = template;
        this.messageRepo = messageRepo;
        this.userSubscriptionRepo = userSubscriptionRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
    }


    private void fillMeta(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMG_REGEX.matcher(url);

            message.setLink(url);

            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);

                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    public void delete(Message message) {
        messageRepo.delete(message);

        sendWsMessage(message.getAuthor(),EventType.REMOVE, message);
    }

    public Message update(User user, Message messageFromDb, Message message) throws IOException {
        if(messageFromDb.getAuthor().equals(user)) {
            messageFromDb.setText(message.getText());
            fillMeta(messageFromDb);
            Message updatedMessage = messageRepo.save(messageFromDb);

            sendWsMessage(messageFromDb.getAuthor(), EventType.UPDATE, updatedMessage);

            return updatedMessage;
        }else return null;
        // yeesss, its null, I change it later
    }

    public Message create(Message message, User user) throws IOException {
        message.setCreationDate(LocalDateTime.now());
        fillMeta(message);
        message.setAuthor(user);
        Message savedMessage = messageRepo.save(message);

        sendWsMessage(user,EventType.CREATE, savedMessage);

        return savedMessage;
    }

    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepo.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepo.findByAuthorIn(channels, pageable);

        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

    private void sendWsMessage(User sender, EventType eventType, Message message){
        List<User> recipients = userSubscriptionRepo.findByChannel(sender)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getSubscriber)
                .collect(Collectors.toList());

        recipients.forEach(user -> wsSender.accept(user, eventType, message));
    }
}

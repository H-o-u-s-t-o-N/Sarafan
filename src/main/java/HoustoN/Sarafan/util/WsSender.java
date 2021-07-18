package HoustoN.Sarafan.util;

import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.dto.WsEventDto;
import HoustoN.Sarafan.dto.enums.EventType;
import HoustoN.Sarafan.dto.enums.ObjectType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WsSender {
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public WsSender(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public <T> CustomConsumer<User, EventType, T> getSender(ObjectType objectType, Class view) {
        ObjectWriter writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(view);
        return (User recipient, EventType eventType, T payload) -> {
            String value = null;
            try {
                 value = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            template.convertAndSendToUser(
                    recipient.toString(),
                    "queue/activity",
                    new WsEventDto(objectType, eventType, value)
                    );
        };
    }
}

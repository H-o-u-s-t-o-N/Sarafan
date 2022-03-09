package HoustoN.Sarafan.dto;

import HoustoN.Sarafan.domain.Message;
import HoustoN.Sarafan.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.List;

@Data
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    private List<Message> messages;
    private int currentPage;
    private int totalPages;

    public MessagePageDto(List<Message> messages, int currentPage, int totalPages) {
        this.messages = messages;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}

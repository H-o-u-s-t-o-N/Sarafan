package HoustoN.Sarafan.dto;

import HoustoN.Sarafan.domain.Views;
import HoustoN.Sarafan.dto.enums.EventType;
import HoustoN.Sarafan.dto.enums.ObjectType;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonView(Views.Id.class)
public class WsEventDto {
    private ObjectType objectType;
    private EventType eventType;

    @JsonRawValue
    private String body;
}

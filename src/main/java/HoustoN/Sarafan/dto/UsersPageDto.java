package HoustoN.Sarafan.dto;

import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.IdName.class)
public class UsersPageDto {
    private List<User> users;
    private int currentPage;
    private int totalPages;
}

package HoustoN.Sarafan.dto;

import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@JsonView(Views.IdName.class)
public class UsersPageDto {
    private List<User> users;
    private int currentPage;
    private int totalPages;

    public UsersPageDto(List<User> users, int currentPage, int totalPages) {
        this.users = users;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}

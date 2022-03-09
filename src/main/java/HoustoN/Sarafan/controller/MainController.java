package HoustoN.Sarafan.controller;

import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.Views;
import HoustoN.Sarafan.dto.MessagePageDto;
import HoustoN.Sarafan.dto.UsersPageDto;
import HoustoN.Sarafan.repo.UserDetailsRepo;
import HoustoN.Sarafan.service.MessageService;
import HoustoN.Sarafan.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final ProfileService profileService;
    private final UserDetailsRepo userDetailsRepo;

    @Value("${spring.profiles.active:prod}")
    private String profile;

    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;
    private final ObjectWriter usersWriter;

    @Autowired
    public MainController(MessageService messageService, UserDetailsRepo userDetailsRepo, ObjectMapper mapper, ProfileService profileService) {
        this.messageService = messageService;
        this.userDetailsRepo = userDetailsRepo;
        this.profileService = profileService;

        ObjectMapper objectMapper = mapper
                .setConfig(mapper.getSerializationConfig());

        this.messageWriter = objectMapper
                .writerWithView(Views.FullMessage.class);
        this.profileWriter = objectMapper
                .writerWithView(Views.FullProfile.class);
        this.usersWriter = objectMapper
                .writerWithView(Views.IdName.class);
    }


    @GetMapping
    public String main(
            Model model,
            @AuthenticationPrincipal User user
    ) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();

        // just init start data
        if (user != null) {
            User userFromDb = userDetailsRepo.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedProfile);

            PageRequest pageRequest = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE);
            MessagePageDto messagePageDto = messageService.findForUser(pageRequest, user);

            String messages = messageWriter.writeValueAsString(messagePageDto.getMessages());

            PageRequest pageRequestUsers = PageRequest.of(0, ProfileController.USERS_PER_PAGE);
            UsersPageDto usersPageDto = profileService.getAllUsers(pageRequestUsers);

            String users = usersWriter.writeValueAsString(usersPageDto.getUsers());

            model.addAttribute("messages", messages);
            model.addAttribute("users", users);
            data.put("MessagesCurrentPage", messagePageDto.getCurrentPage());
            data.put("UsersCurrentPage", usersPageDto.getCurrentPage());
            data.put("MessagesTotalPages", messagePageDto.getTotalPages());
            data.put("UsersTotalPages", usersPageDto.getTotalPages());
        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
            model.addAttribute("users", "[]");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
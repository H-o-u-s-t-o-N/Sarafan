package HoustoN.Sarafan.controller;

import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.UserSubscription;
import HoustoN.Sarafan.domain.Views;
import HoustoN.Sarafan.dto.UsersPageDto;
import HoustoN.Sarafan.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("all")
    @JsonView(Views.IdName.class)
    public UsersPageDto getAll(
            @PageableDefault(size = 20, sort = { "name" }, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return profileService.getAllUsers(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(
            @AuthenticationPrincipal User subscriber,
            @PathVariable("channelId") User channel
    ) {
        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscribers(
            @PathVariable("channelId") User channel
    ) {
        return profileService.getSubscribers(channel);
    }
    @GetMapping("get-channels/{subscriberId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscriptions(
            @PathVariable("subscriberId") User subscriber
    ){
        return profileService.getSubscriptions(subscriber);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscription changeSubscriptionStatus(
            @AuthenticationPrincipal User channel,
            @PathVariable("subscriberId") User subscriber
    ) {
        return profileService.changeSubscriptionStatus(channel, subscriber);
    }
}

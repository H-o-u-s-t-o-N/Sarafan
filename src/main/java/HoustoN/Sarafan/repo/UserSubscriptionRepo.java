package HoustoN.Sarafan.repo;

import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.domain.UserSubscription;
import HoustoN.Sarafan.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
}
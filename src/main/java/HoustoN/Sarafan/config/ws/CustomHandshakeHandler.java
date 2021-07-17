package HoustoN.Sarafan.config.ws;

import HoustoN.Sarafan.domain.User;
import HoustoN.Sarafan.dto.UserWsConfigDto;
import HoustoN.Sarafan.repo.UserDetailsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @SneakyThrows
    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

//        ObjectMapper mapper = new ObjectMapper();
//        UserWsConfigDto person = mapper.readValue(request.getPrincipal().getName(), UserWsConfigDto.class);

        System.out.println(request.getPrincipal().getName());
        return new CustomPrincipal(request.getPrincipal().getName());
    }


}

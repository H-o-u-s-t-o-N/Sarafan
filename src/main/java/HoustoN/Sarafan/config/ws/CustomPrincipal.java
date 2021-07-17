package HoustoN.Sarafan.config.ws;

import HoustoN.Sarafan.domain.User;

import java.security.Principal;


public class CustomPrincipal implements Principal {
    private String loginName;

    public CustomPrincipal(String loginUser) {this.loginName = loginUser;}

    @Override
    public String getName() {
        return loginName;
    }
}

package fudan.se.lab2.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Some properties about JWT.
 * You can change the value in `application.properties`.
 *
 * @author LBW
 */
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtConfigPro {
    @Value("${jwt.token.validity}")
    private int validity;
    @Value("${jwt.token.secret}")
    private String secret;

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

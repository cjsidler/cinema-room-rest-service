package cinema;

import java.util.UUID;

public class TokenRequest {
    private UUID token;

    public TokenRequest() {
    }

    public TokenRequest(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID uuid) {
        this.token = uuid;
    }
}

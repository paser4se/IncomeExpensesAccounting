package at.htl.iea.rest.auth;

import at.htl.iea.dao.AccountDao;
import at.htl.iea.model.Account;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Provider
@Secured
public class AuthentificationFilter implements ContainerRequestFilter {
    @Inject
    AccountDao accountDao;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Map<String, Cookie> cockies = containerRequestContext.getCookies();
        if (cockies.containsKey("auth-token")) {
            checkToken(cockies.get("auth-token"), containerRequestContext);
        } else {
            abort(containerRequestContext);
        }
    }

    private void checkToken(Cookie cookie, ContainerRequestContext context) {
        try {
            String token = cookie.getValue();
            String decodedToken = new String(Base64.getDecoder().decode(token));
            String[] decodedTokenParts = decodedToken.split(":");
            String username = decodedTokenParts[0];
            String password = decodedTokenParts[1];

            Account account = accountDao.getAccountByUsername(username);
            if (!password.equals(account.getPassword())) {
                abort(context);
            }
        } catch (Exception ex) {
            System.err.println("Fehler bei der Authentifikation");
            abort(context);
        }
    }

    private void abort(ContainerRequestContext context) {
        context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}

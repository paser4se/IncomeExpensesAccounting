package at.htl.iea.rest;

import at.htl.iea.business.Parser;
import at.htl.iea.dao.AccountDao;
import at.htl.iea.model.Account;
import at.htl.iea.model.Payment;
import org.jboss.resteasy.annotations.Body;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Base64;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Cookie;

@Path("auth")
public class AccountAuthentificationEndPoint {

    @Inject
    AccountDao accountDao;

    private NewCookie buildCookie(String token){
        long maxAge = LocalDate
            .now()
            .plusDays(2)
            .atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond(); // converting date to seconds

        return new NewCookie(
            "auth-token", 
            token,
            "/",
            null,
            0,
            null,
            (int)maxAge, //if maxAge = 0 --> cookie will be deleted; if maxAge < 0 --> cookie will be deleted when the web broswser is exited
            null,
            false,
            true
        );
    }

    private boolean doesUserExist(String username, String password){
        try{
            Account account = accountDao.getAccountByUsername(username);
            return account.getPassword() == password;
        } catch(EJBException  ex){
            if (ex.getCausedByException() instanceof NoResultException){
                System.out.println("Account does not exist");
            }
            else {
                ex.printStackTrace();
            }
            return false;
        }
    }

    /*TODO: evtl. oauth2 george and elba
        oauth2 explanation https://www.youtube.com/watch?v=996OiexHze0
        george https://developers.erstegroup.com/docs/apis/bank.csas/bank.csas.v3%2Fnetbanking
            scopes: transaction.history.read
        elba R.I.P
    */

    /* Paste into console to test authenticate
        fetch("http://localhost:8080/iea/rs/auth/authenticate", {
            method: "post",
            credentials: "include"
        })
    */

    // muss über die console gemacht werden --> überprüft DIREKT am anfang, ob der token valide ist [request an /authenticate schicken]
    @POST
    @Path("authenticate")
    public Response authenticate(@CookieParam("auth-token") Cookie cookie){
        if(cookie == null){ // log in has not yet happened
            return Response.status(400).build();
        }

        String token = cookie.getValue();
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] decodedTokenParts = decodedToken.split(":");
        String username = decodedTokenParts[0];
        String password = decodedTokenParts[1];

        // check for valid user
        if(!doesUserExist(username, password)){
            System.out.println("USERNAME/PASSWORD is not correct!");
            // delete cookie
            return Response
                .status(404)
                .header("Set-Cookie", "auth-token=deleted;Path=/;max-age=0;HttpOnly")
                .build();
        }
        System.out.println("TESTTESTTESTTESTTESTTEST");
        // return new cookie to extend the lifespan of the cookie
        return Response
            .ok()
            .cookie(buildCookie(token))
            .build();
    }

    @POST
    @Path("logout")
    public Response logout(){
        // delete cookie
        return Response
                .status(404)
                .header("Set-Cookie", "auth-token=deleted;Path=/;max-age=0;HttpOnly")
                .build(); 
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(JsonValue json) {
        JsonObject body = json.asJsonObject();
        try{
            String username = body.getString("username");
            String password = body.getString("password");
            //String email = body.getString("email"); // TODO: erweitern in .html file
            //TODO: weitere fields anpassen je nach Datenmodell (zB age, ...)
            return Response 
                .ok()
                .entity("ID")
                .build(); // access token wird nicht gebraucht, da dieser erst bei der authenfikation benötigt wird
        } catch(Exception ex){
            ex.printStackTrace();
            System.err.println("[ERROR]: AccountAuthentificationEndPoint, Method: login");
            return Response.status(500).build();
        }
    }    

    @POST
    @Path("login")    
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(JsonValue json) {
        JsonObject body = json.asJsonObject();
        try{
            String username = body.getString("username");
            String password = body.getString("password");
            String accessToken = Base64
                .getEncoder()
                .encodeToString((username + ":" + password).getBytes()); // basic auth procedure

            return Response
                .ok()
                .cookie(buildCookie(accessToken))
                .build();
        } catch(Exception ex){
            ex.printStackTrace();
            System.err.println("[ERROR]: AccountAuthentificationEndPoint, Method: login");
            return Response.status(500).build();
        }
    }

}

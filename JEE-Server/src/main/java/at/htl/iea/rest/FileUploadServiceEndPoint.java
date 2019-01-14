package at.htl.iea.rest;

import at.htl.iea.business.Parser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

@Path("files")
public class  FileUploadServiceEndPoint {

    @GET
    public Response hello() {
        return Response.status(Response.Status.OK).entity("Connected to project IEA ...").build();
    }

    @POST
    @Path("uploadtext")
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response uploadText(String content) {
        try {
            Parser.getInstance().persist(content);
        } catch (ParseException e) {
            System.err.println("ParseException: " + e.getMessage());
            return Response.serverError().build();
        } catch (NoSuchFieldException e) {
            System.err.println("NoSuchFieldException: " + e.getMessage());
            return Response.serverError().build();
        }
        return Response.ok("content recveived").build();
    }
}
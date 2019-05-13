package at.htl.iea.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {
  //  
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
          /* e.g.
            options request von test2.com 
            -> server returned headers 
            -> browser vergleicht test2.com origin mit allowed-origin
            if test2.com steht in allowed-origin
              -> request geht durch
            else
              -> R.I.P
          */
          responseContext.getHeaders().add(
            "Access-Control-Allow-Origin", 
            requestContext.getHeaders().getFirst("Origin")
          );
          responseContext.getHeaders().add(
            "Access-Control-Allow-Credentials", "true");
          responseContext.getHeaders().add(
           "Access-Control-Allow-Headers",
           "origin, content-type, accept, authorization");
          responseContext.getHeaders().add(
            "Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
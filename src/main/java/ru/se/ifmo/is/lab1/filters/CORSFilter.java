package ru.se.ifmo.is.lab1.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        String origin = requestContext.getHeaderString("Origin");
        if (origin == null) {
            origin = "*";
        }

        responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, x-requested-with, cache-control, access-control-allow-origin, access-control-allow-headers");
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        responseContext.getHeaders().add("Access-Control-Expose-Headers",
                "authorization, content-type, location");
        responseContext.getHeaders().add("Access-Control-Max-Age", "3600");

        // Handle preflight requests
        if ("OPTIONS".equals(requestContext.getMethod())) {
            responseContext.setStatus(200);
        }
    }
}
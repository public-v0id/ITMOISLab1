package ru.se.ifmo.is.lab1.resource;

import ru.se.ifmo.is.lab1.beans.ImportOperation;
import ru.se.ifmo.is.lab1.repository.ImportRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/import/history")
public class ImportHistoryResource {

    @Inject
    private ImportRepository importRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImportOperation> history(
            @QueryParam("username") String username,
            @QueryParam("admin") boolean admin
    ) {
        if (admin) {
            return importRepository.findAll();
        }
        return importRepository.findByUser(username);
    }
}

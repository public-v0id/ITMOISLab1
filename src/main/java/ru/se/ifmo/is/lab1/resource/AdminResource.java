package ru.se.ifmo.is.lab1.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.se.ifmo.is.lab1.dto.CoordinatesDTO;
import ru.se.ifmo.is.lab1.mapper.CoordinatesMapper;
import ru.se.ifmo.is.lab1.repository.CoordinatesRepository;
import ru.se.ifmo.is.lab1.repository.MovieRepository;
import ru.se.ifmo.is.lab1.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    private PersonRepository personRepository;

    @Inject
    private MovieRepository movieRepository;

    @DELETE
    @Path("/person/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        personRepository.delete(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/movie/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        movieRepository.deleteById(id);
        return Response.ok().build();
    }
}
package ru.se.ifmo.is.lab1.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.se.ifmo.is.lab1.beans.MovieGenre;
import ru.se.ifmo.is.lab1.dto.PersonDTO;
import ru.se.ifmo.is.lab1.mapper.PersonMapper;
import ru.se.ifmo.is.lab1.repository.LocationRepository;
import ru.se.ifmo.is.lab1.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    @Inject
    private PersonRepository personRepository;

    @Inject
    private LocationRepository locationRepository;

    @GET
    public Response getAllPersons() {
        List<PersonDTO> list = personRepository.findAll()
                .stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        var person = personRepository.findById(id);
        return person != null
                ? Response.ok(PersonMapper.toDTO(person)).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createPerson(PersonDTO dto) {
        var entity = PersonMapper.fromDTO(dto, locationRepository.findById(dto.locationID));
        personRepository.save(entity);
        return Response.ok(PersonMapper.toDTO(entity)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") Long id, PersonDTO dto) {
        var existing = personRepository.findById(id);
        if (existing == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        var updated = PersonMapper.fromDTO(dto, locationRepository.findById(dto.locationID));
        updated.setId(id);
        personRepository.update(updated);
        return Response.ok(PersonMapper.toDTO(updated)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        personRepository.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/directors/no-oscars")
    public Response getDirectorsWithoutOscars() {
        return Response.ok(
                personRepository.findDirectorsWithoutOscars()
                        .stream()
                        .map(PersonMapper::toDTO)
                        .collect(Collectors.toList())
        ).build();
    }

    @GET
    @Path("/directors/by-genre")
    public Response getDirectorsByGenre(@QueryParam("genre") MovieGenre genre) {
        return Response.ok(
                personRepository.findDirectorsWithGenre(genre)
                        .stream()
                        .map(PersonMapper::toDTO)
                        .collect(Collectors.toList())
        ).build();
    }

    @POST
    @Path("/directors/remove-oscars")
    public Response removeOscarsFromDirectors(@QueryParam("genre") MovieGenre genre) {
        int updated = personRepository.removeOscarsFromDirectorsWithGenre(genre);
        return Response.ok("{\"updated\":" + updated + "}").build();
    }

    @OPTIONS
    @Path("{path: .*}")
    public Response options() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }
}
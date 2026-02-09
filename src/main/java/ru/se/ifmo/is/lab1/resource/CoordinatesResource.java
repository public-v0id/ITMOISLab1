package ru.se.ifmo.is.lab1.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.se.ifmo.is.lab1.dto.CoordinatesDTO;
import ru.se.ifmo.is.lab1.mapper.CoordinatesMapper;
import ru.se.ifmo.is.lab1.repository.CoordinatesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Path("/coordinates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoordinatesResource {

    @Inject
    private CoordinatesRepository coordinatesRepository;

    @GET
    public Response getAll() {
        List<CoordinatesDTO> list = coordinatesRepository.findAll()
                .stream()
                .map(CoordinatesMapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        var c = coordinatesRepository.findById(id);
        return c != null ? Response.ok(CoordinatesMapper.toDTO(c)).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response create(CoordinatesDTO dto) {
        var entity = CoordinatesMapper.fromDTO(dto);
        coordinatesRepository.save(entity);
        return Response.ok(CoordinatesMapper.toDTO(entity)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CoordinatesDTO dto) {
        var existing = coordinatesRepository.findById(id);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();

        var updated = CoordinatesMapper.fromDTO(dto);
        updated.setId(id);
        coordinatesRepository.update(updated);
        return Response.ok(CoordinatesMapper.toDTO(updated)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        coordinatesRepository.delete(id);
        return Response.noContent().build();
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
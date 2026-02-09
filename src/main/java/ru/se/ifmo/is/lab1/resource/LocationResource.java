package ru.se.ifmo.is.lab1.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.se.ifmo.is.lab1.dto.LocationDTO;
import ru.se.ifmo.is.lab1.mapper.LocationMapper;
import ru.se.ifmo.is.lab1.repository.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {

    @Inject
    private LocationRepository locationRepository;

    @GET
    public Response getAll() {
        List<LocationDTO> list = locationRepository.findAll()
                .stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        var l = locationRepository.findById(id);
        return l != null ? Response.ok(LocationMapper.toDTO(l)).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response create(LocationDTO dto) {
        var entity = LocationMapper.fromDTO(dto);
        locationRepository.save(entity);
        return Response.ok(LocationMapper.toDTO(entity)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, LocationDTO dto) {
        var existing = locationRepository.findById(id);
        if (existing == null) return Response.status(Response.Status.NOT_FOUND).build();

        var updated = LocationMapper.fromDTO(dto);
        updated.setId(id);
        locationRepository.update(updated);
        return Response.ok(LocationMapper.toDTO(updated)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        locationRepository.delete(id);
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
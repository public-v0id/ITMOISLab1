package ru.se.ifmo.is.lab1.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import ru.se.ifmo.is.lab1.beans.Movie;
import ru.se.ifmo.is.lab1.beans.MovieGenre;
import ru.se.ifmo.is.lab1.dto.MovieDTO;
import ru.se.ifmo.is.lab1.mapper.MovieMapper;
import ru.se.ifmo.is.lab1.repository.CoordinatesRepository;
import ru.se.ifmo.is.lab1.repository.LocationRepository;
import ru.se.ifmo.is.lab1.repository.MovieRepository;
import ru.se.ifmo.is.lab1.repository.PersonRepository;
import ru.se.ifmo.is.lab1.websockets.MovieWebSocket;

import java.util.List;
import java.util.stream.Collectors;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieRepository movieRepository;
    @Inject
    private PersonRepository personRepository;
    @Inject
    private LocationRepository locationRepository;
    @Inject
    private CoordinatesRepository coordinatesRepository;

    @Inject
    private MovieWebSocket movieWebSocket;

    @GET
    public Response getAllMovies() {
        List<MovieDTO> movies = movieRepository.findAll()
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @GET
    @Path("/{id}")
    public Response getMovieById(@PathParam("id") Long id) {
        Movie movie = movieRepository.find(id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(MovieMapper.toDTO(movie)).build();
    }

    @POST
    public Response createMovie(MovieDTO dto, @Context UriInfo uriInfo) {
        Movie movie = MovieMapper.fromDTO(dto, personRepository.findById(dto.director), personRepository.findById(dto.screenwriter), personRepository.findById(dto.operator), coordinatesRepository.findById(dto.coordinates));
        movieRepository.save(movie);
        movieWebSocket.broadcast("{\"event\":\"create\",\"id\":" + movie.getId() + "}");

        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(movie.getId()));
        return Response.created(builder.build()).entity(MovieMapper.toDTO(movie)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") Long id, MovieDTO dto) {
        Movie existing = movieRepository.find(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Movie updated = MovieMapper.fromDTO(dto, personRepository.findById(dto.director), personRepository.findById(dto.screenwriter), personRepository.findById(dto.operator), coordinatesRepository.findById(dto.coordinates));
        updated.setId(id);
        movieRepository.update(updated);
        movieWebSocket.broadcast("{\"event\":\"update\",\"id\":" + id + "}");

        return Response.ok(MovieMapper.toDTO(updated)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        movieRepository.deleteById(id);
        movieWebSocket.broadcast("{\"event\":\"delete\",\"id\":" + id + "}");
        return Response.noContent().build();
    }

    @GET
    @Path("/filter/by-name")
    public Response getMoviesByName(@QueryParam("prefix") String prefix) {
        List<MovieDTO> movies = movieRepository.findByNameStartsWith(prefix)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @GET
    @Path("/filter/by-genre-less")
    public Response getMoviesByGenreLess(@QueryParam("genre") MovieGenre genre) {
        List<MovieDTO> movies = movieRepository.findByGenreLessThan(genre)
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @GET
    @Path("/unique/usaBoxOffice")
    public Response getUniqueUsaBoxOffice() {
        List<Integer> values = movieRepository.uniqueUsaBoxOffice();
        return Response.ok(values).build();
    }

    @OPTIONS
    @Path("{path: .*}")
    public Response options() {
        return Response.ok()
                .build();
    }
}
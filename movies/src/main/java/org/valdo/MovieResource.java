package org.valdo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

@Path("/movies")
public class MovieResource {

    public static List<Movie> movies = new ArrayList<Movie>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countMovies() {
        return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON) 
    public Response createMovie(Movie newMovie) {
        movies.add(newMovie);
        return Response.ok(movies).build();
    }

    @PUT 
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") Long id,@QueryParam("movie") String title) {
        movies = movies.stream().map(movie -> {
            if (movie.getId().equals(id)) {
                movie.setTitle(title);
            }
            return movie;
        }).collect(Collectors.toList());

        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteMovie(@PathParam("id") Long id) {
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();
        boolean removed = false;
        if (movieToDelete.isPresent()) {
            removed = movies.remove(movieToDelete.get());
        }
        return removed ? Response.noContent().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}

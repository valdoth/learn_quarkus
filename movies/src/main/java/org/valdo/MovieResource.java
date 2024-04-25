package org.valdo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
@Tag(name = "Movie Resource", description="Movie REST APIs")
public class MovieResource {

    public static List<Movie> movies = new ArrayList<Movie>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        operationId = "getMovies",
        summary = "get movies",
        description = "Get all movies inside the list"
    )
    @APIResponse(
        responseCode = "200",
        description = "operation completed",
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getMovies() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    @Operation(
        operationId = "countMovies",
        summary = "count movies",
        description = "Size of the list movies"
    )
    @APIResponse(
        responseCode = "200",
        description = "operation completed",
        content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Integer countMovies() {
        return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON) 
    @Operation(
        operationId = "createMovie",
        summary = "create movies",
        description = "Create a new movie to add inside the list"
    )
    @APIResponse(
        responseCode = "201",
        description = "movie created",
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @RequestBody(
        description = "Movie to create",
        required = true,
        content = @Content(schema = @Schema(implementation = Movie.class))
    )    
    public Response createMovie(Movie newMovie) {
        movies.add(newMovie);
        // return Response.ok(movies).build();
        return Response.status(Response.Status.CREATED).entity(movies).build();
    }

    @PUT 
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        operationId = "updateMovie",
        summary = "update movie",
        description = "update a movie inside the list"
    )
    @APIResponse(
        responseCode = "200",
        description = "movie updated",
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response updateMovie(
        @Parameter(
            description = "Movie id",
            required = true
        )
        @PathParam("id") Long id,
        @Parameter(
            description = "Movie title",
            required = true
        )
        @QueryParam("movie") String title
    ) {
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
    @Operation(
        operationId = "deleteMovie",
        summary = "delete movie",
        description = "delete a movie inside the list"
    )
    @APIResponse(
        responseCode = "204",
        description = "movie deleted",
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
        responseCode = "400",
        description = "movie not valid",
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteMovie(@PathParam("id") Long id) {
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();
        boolean removed = false;
        if (movieToDelete.isPresent()) {
            removed = movies.remove(movieToDelete.get());
        }
        return removed ? Response.noContent().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}

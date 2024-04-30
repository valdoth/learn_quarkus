package org.valdo;

import java.net.URI;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
public class MovieResource {

    @Inject
    PgPool client;

    @PostConstruct
    void config() {
        initdb();
    }

    private void initdb() {
        client.query("DROP TABLE IF EXISTS movies").execute()
            .flatMap(m -> client.query("CREATE TABLE movies (id SERIAL PRIMARY KEY, title TEXT NOT NULL)").execute())
            .flatMap(m -> client.query("INSERT INTO movies (title) VALUES('The Lord of the Rings')").execute())
            .flatMap(m -> client.query("INSERT INTO movies (title) VALUES('Harry Potter')").execute())
            .await()
            .indefinitely();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Movie> get() {
        return Movie.findAll(client);
    }

    @GET
    @Path("/{id}")
    public Uni<Movie> get(@PathParam("id") Long id) {
        return Movie.findById(client, id);
    }

    @POST
    public Uni<Response> create(Movie movie) {
        return Movie.save(client, movie.getTitle())
                .onItem()
                .transform(id -> URI.create("/movies/" + id))
                .onItem()
                .transform(uri -> Response.created(uri).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return Movie.delete(client, id)
                .onItem()
                .transform(deleted -> deleted ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND)
                .onItem()
                .transform(status -> Response.status(status).build());
    }

    
} 

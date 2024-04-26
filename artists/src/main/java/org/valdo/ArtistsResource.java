package org.valdo;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Path("/artists")
public class ArtistsResource {
  
  private static List<Artist> artists = List.of(
    new Artist().id(UUID.randomUUID()).firstName("John").lastName("Lennon"),
    new Artist().id(UUID.randomUUID()).firstName("Paul").lastName("McCartney"),
    new Artist().id(UUID.randomUUID()).firstName("George").lastName("Harrison"),
    new Artist().id(UUID.randomUUID()).firstName("Ringo").lastName("Starr"),
    new Artist().id(UUID.randomUUID()).firstName("Marie").lastName("LeClair"),
    new Artist().id(UUID.randomUUID()).firstName("Katherine").lastName("Dupond")
  );

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllArtists() {
    System.out.println(artists);
    return Response.ok(artists).build();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getArtistById(@PathParam("id") UUID id) {
    return Response.ok(artists.stream().filter(artist -> artist.getId().equals(id)).findFirst()).build();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteArtistById(@PathParam("id") UUID id) {
    return Response.ok(artists.stream().filter(artist -> !artist.getId().equals(id)).collect(Collectors.toList())).build();
  }
    
  @GET
  @Path("/count")
  @Produces(MediaType.TEXT_PLAIN)
    public Integer countArtists() {
    return artists.size();
  }
}
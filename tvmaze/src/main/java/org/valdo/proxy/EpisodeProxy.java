package org.valdo.proxy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.valdo.model.Episode;

@Path("/shows")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface EpisodeProxy {
    @GET
    @Path("/{id}/episodes")
    List<Episode> get(@PathParam("id") Long id);   
}

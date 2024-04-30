package org.valdo.proxy;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.valdo.model.TvSerie;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/singlesearch/")
@Produces(MediaType.APPLICATION_JSON)
// @RegisterRestClient(baseUri = "http://api.tvmaze.com")
@RegisterRestClient
public interface TvSeriesProxy {
    @GET
    @Path("/shows")
    TvSerie get(@QueryParam("q") String title);

}

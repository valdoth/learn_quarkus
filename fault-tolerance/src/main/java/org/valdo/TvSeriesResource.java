package org.valdo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.valdo.model.Episode;
import org.valdo.model.TvSerie;
import org.valdo.proxy.EpisodeProxy;
import org.valdo.proxy.TvSeriesProxy;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tvseries")
public class TvSeriesResource {

    @RestClient
    @Inject
    TvSeriesProxy tvSeriesProxy;

    @RestClient
    @Inject
    EpisodeProxy episodeProxy;
    
    private List<TvSerie> tvSeriesList = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("title") String title) {
        TvSerie tvSeries = tvSeriesProxy.get(title);
        List<Episode> episodes = episodeProxy.get(tvSeries.getId());
        tvSeriesList.add(tvSeries);
        return Response.ok(episodes).build();
    }
}

package com.spintech.testtask.service.tmdb;

public interface TmdbApi {

    String getPopularTVShows();

    String getShowById(Long showId);

    String getPersonById(Long personId);
}

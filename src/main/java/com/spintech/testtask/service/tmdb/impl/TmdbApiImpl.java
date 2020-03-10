package com.spintech.testtask.service.tmdb.impl;

import com.spintech.testtask.service.tmdb.TmdbApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
@Slf4j
public class TmdbApiImpl implements TmdbApi {
    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    public String getPopularTVShows() {
        return getResourceById("/tv/popular");
    }

    public String getPersonById(Long personId) {
        return getResourceById("/person/" + personId);
    }

    public String getShowById(Long showId) {
        return getResourceById("/tv/" + showId);
    }

    private String getResourceById(String resourceName) {
        try {
            String url = getTmdbUrl(resourceName);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody();
        } catch (URISyntaxException e) {
            log.error("Couldn't get actor");
        }
        return null;
    }

    private String getTmdbUrl(String tmdbItem) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(tmdbApiBaseUrl + tmdbItem);
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        return uriBuilder.build().toString();
    }
}

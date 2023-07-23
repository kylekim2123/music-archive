package com.programmers.musicapp.spotify;

import static com.programmers.musicapp.exception.ExceptionRule.INTERNAL_SERVER_ERROR_500;

import com.programmers.musicapp.dto.request.MusicCreateRequest;
import com.programmers.musicapp.service.MusicService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SpotifyWebApi implements ApplicationRunner {

    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String TOP_MUSIC_URL = "https://api.spotify.com/v1/playlists/37i9dQZEVXbMDoHDwVN2tF";

    private final MusicService musicService;
    private final RestTemplate restTemplate = new RestTemplate();
    private boolean alreadySetup = false;

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Override
    public void run(ApplicationArguments args) {
        if (alreadySetup) {
            return;
        }

        String accessToken = requestAccessToken();
        List<MusicCreateRequest> topMusics = requestTopMusics(accessToken);
        saveTopMusics(topMusics);

        alreadySetup = true;
    }

    private void saveTopMusics(List<MusicCreateRequest> topMusics) {
        topMusics.forEach(musicService::createMusic);
    }

    private List<MusicCreateRequest> requestTopMusics(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<TopMusicsResponse> response = restTemplate.exchange(TOP_MUSIC_URL, HttpMethod.GET, request, TopMusicsResponse.class);

        List<SpotifyItem> items = response.getBody().getTracks().getItems();
        List<MusicCreateRequest> topMusics = new ArrayList<>();

        for (SpotifyItem item : items) {
            String title = item.getTrack().getName();
            String posterUrl = item.getTrack().getAlbum().getImages().get(0).getUrl();
            String artistName = item.getTrack().getArtists().get(0).getName();
            String releasedDate = item.getTrack().getAlbum().getReleaseDate();

            topMusics.add(MusicCreateRequest.builder()
                    .title(title)
                    .posterUrl(posterUrl)
                    .description("")
                    .artistName(artistName)
                    .releasedDate(LocalDate.parse(releasedDate))
                    .isSpotify(true)
                    .build());
        }

        return topMusics;
    }

    private String requestAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, AccessTokenResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getAccessToken();
        }

        throw new RuntimeException(INTERNAL_SERVER_ERROR_500.getMessage());
    }
}

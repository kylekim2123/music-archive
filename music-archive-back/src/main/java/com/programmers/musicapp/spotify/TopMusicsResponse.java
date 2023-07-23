package com.programmers.musicapp.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class TopMusicsResponse {

    @JsonProperty("tracks")
    private SpotifyTracks tracks;
}

@Getter
class SpotifyTracks {

    @JsonProperty("items")
    private List<SpotifyItem> items;
}

@Getter
class SpotifyItem {

    @JsonProperty("track")
    private SpotifyTrack track;
}

@Getter
class SpotifyTrack {

    @JsonProperty("name")
    private String name;

    @JsonProperty("album")
    private SpotifyAlbum album;

    @JsonProperty("artists")
    private List<SpotifyArtist> artists;
}

@Getter
class SpotifyAlbum {

    @JsonProperty("images")
    private List<SpotifyImage> images;

    @JsonProperty("release_date")
    private String releaseDate;
}

@Getter
class SpotifyArtist {

    @JsonProperty("name")
    private String name;
}

@Getter
class SpotifyImage {

    @JsonProperty("url")
    private String url;
}
package ru.pavel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaGet {
    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private String url;

    public NasaGet(
            @JsonProperty("date") String date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdurl,
            @JsonProperty("media_type") String media_type,
            @JsonProperty("service_version") String service_version,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url
    ) {
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getUrl() {
        return url;
    }

    public String getExplanation() {return explanation;}

    @Override
    public String toString() {
        return "NasaGet{" +
                ", date='" + date + "\n" +
                ", explanation='" + explanation + "\n" +
                ", hdurl='" + hdurl + "\n" +
                ", media_type='" + media_type + "\n" +
                ", service_version='" + service_version + "\n" +
                ", title='" + title + "\n" +
                ", url='" + url + "\n" +
                '}';
    }
}

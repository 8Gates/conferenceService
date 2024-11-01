package com.pluralsight.client;

import com.pluralsight.model.Speaker;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpeakerSearchClient {
    private Client client;
    private final String SPEAKER_URI = "http://localhost:8080/speaker";
    private final String SPEAKER_SEARCH_URI = "http://localhost:8080/search/speaker";

    public SpeakerSearchClient(){
        client = ClientBuilder.newClient();
    }

    public List<Speaker> search(String param, List<String> searchValues) {
        WebTarget target = client.target(SPEAKER_SEARCH_URI);
        for (String value : searchValues) {
            target = target.queryParam(param, value);
        }
        return target
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Speaker>>() {});
    }

    public Speaker post(Speaker speaker){
        Response response = client
                .target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(speaker, MediaType.APPLICATION_JSON));
        return response.readEntity(Speaker.class);
    }

    public static void main(String args[]){
        Speaker speaker = new Speaker();
        SpeakerSearchClient client = new SpeakerSearchClient();


        speaker.setName("Alexis");
        speaker.setCompany("Amazon");
        speaker = client.post(speaker);

        speaker.setName("XNL-350");
        speaker.setCompany("Sony");
        speaker = client.post(speaker);

        speaker.setName("Jarvis");
        speaker.setCompany("Sony");
        client.post(speaker);

        List<Speaker> results = client.search("company", Arrays.asList("Sony", "Amazon"));
        System.out.println("Results of speakers matching company name search Sony and Amazon:");
        String namesOfMatchingSpeakers = results.stream()
                .map(Speaker::getName)
                .collect(Collectors.joining(", "));
        System.out.println(namesOfMatchingSpeakers);
    }
}

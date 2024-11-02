package com.pluralsight.client;

import com.pluralsight.model.Speaker;
import com.pluralsight.model.SpeakerSearch;
import com.pluralsight.model.SpeakerSearchType;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpeakerSearchClient {
    private Client client;
    private final String SPEAKER_URI = "http://localhost:8080/speaker";
    private final String SPEAKER_SEARCH_URI = "http://localhost:8080/search/speaker";
    private final String SPEAKER_REMOVE_ALL_URI = "http://localhost:8080/speaker/removeall";

    public SpeakerSearchClient(){
        client = ClientBuilder.newClient();
    }

    public List<Speaker> searchWithObject(SpeakerSearch speakerSearch){
        Response response = client
                .target(SPEAKER_SEARCH_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(speakerSearch, MediaType.APPLICATION_JSON));

        List<Speaker> speakers = response.readEntity(new GenericType<List<Speaker>>(){});
        if(response.getStatus()!=200) throw new RuntimeException();
        return speakers;
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

    public List<Speaker> get(){
        Response response = client
                .target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .get();

        return response.readEntity(new GenericType<List<Speaker>>(){});
    }

    public Speaker post(Speaker speaker){
        Response response = client
                .target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(speaker, MediaType.APPLICATION_JSON));
        return response.readEntity(Speaker.class);
    }

    private void deleteAll(){
        Response response = client
                .target(SPEAKER_REMOVE_ALL_URI)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    public static void main(String args[]){
        SpeakerSearchClient client = new SpeakerSearchClient();

        client.post(new Speaker("Alexis", "Amazon"));
        client.post(new Speaker("XNL-350", "Sony"));
        client.post(new Speaker("Jarvis", "Sony"));

        List<Speaker> results = client.search("company", Arrays.asList("Sony", "Amazon"));
        System.out.println("Results of speakers matching company name search Sony and Amazon:");
        String namesOfMatchingSpeakers = results.stream()
                .map(Speaker::getName)
                .collect(Collectors.joining(", "));
        System.out.println(namesOfMatchingSpeakers);

        SpeakerSearch speakerSearch = new SpeakerSearch();
        speakerSearch.setCompanies(Arrays.asList("XNL-350", "Sony"));
        speakerSearch.setAgeFrom(20);
        speakerSearch.setAgeTo(80);
        speakerSearch.setSearchType(SpeakerSearchType.SEARCH_BY_COMPANY);
        results = client.searchWithObject(speakerSearch);
        System.out.println(results.size());

        System.out.println("Removing all speakers.");
        client.deleteAll();
        System.out.println("Number of speakers: " + client.get().size());
    }
}

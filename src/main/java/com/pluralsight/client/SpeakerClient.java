package com.pluralsight.client;
import com.pluralsight.model.Speaker;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Stream;

public class SpeakerClient {
    private Client client;
    private final String SPEAKER_URI = "http://localhost:8080/speaker";
    private final String SPEAKER_REMOVE_ALL_URI = "http://localhost:8080/speaker/removeall";

    public SpeakerClient(){
        client = ClientBuilder.newClient();
    }

    public Speaker get(Long l){
        return client
                .target(SPEAKER_URI)
                .path(String.valueOf(l))
                .request(MediaType.APPLICATION_JSON)
                .get(Speaker.class);
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

    public Speaker put(Speaker speaker){
        Response response = client
                .target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(speaker, MediaType.APPLICATION_JSON));
        return response.readEntity(Speaker.class);
    }

    private void delete(Long id) {
        Response response = client
                .target(SPEAKER_URI)
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    private void deleteAll(){
        Response response = client
                .target(SPEAKER_REMOVE_ALL_URI)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    public static void main (String[] args){
        SpeakerClient client = new SpeakerClient();
        Speaker speaker = new Speaker();
        System.out.println("Number of speakers at start: " + client.get().size());

        speaker.setName("Alexis");
        speaker.setCompany("Amazon");
        speaker = client.post(speaker);
        System.out.println("Created speaker id " + speaker.getId() + ", " + speaker.getName() + ", " + speaker.getCompany());

        speaker.setName("XNL-350");
        speaker.setCompany("Sony");
        speaker = client.post(speaker);
        System.out.println("Created speaker id " + speaker.getId() + ", " + speaker.getName() + ", " + speaker.getCompany());

        System.out.println("Number of speakers: " + client.get().size());
        speaker.setCompany("Wayne Enterprises");
        speaker=client.put(speaker);
        System.out.println("Updated speaker id " + speaker.getId() + ", " + speaker.getName() + ", " + speaker.getCompany());

        System.out.println("Removing all speakers.");
        client.deleteAll();
        System.out.println("Number of speakers at end: " + client.get().size());
    }
}

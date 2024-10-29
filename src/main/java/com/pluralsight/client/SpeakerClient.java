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

    public static void main (String[] args){
        SpeakerClient client = new SpeakerClient();
        Speaker speaker = client.get(1L);
        System.out.println(speaker.getName());

        List<Speaker> speakers = client.get();
        // speakers.stream().map(Speaker::getName).forEach(System.out::println);
        for(Speaker s : speakers){
            System.out.println(s.getName());
        }

        speaker = new Speaker();
        speaker.setName("Alexis");
        speaker.setCompany("Amazon");
        speaker = client.post(speaker);
        System.out.println("SpeakerClient added new speaker "+ speaker.getName());

        speaker.setCompany("Wayne Enterprises");
        speaker=client.put(speaker);
        System.out.println("Updated speaker company " + speaker.getCompany());
    }
}

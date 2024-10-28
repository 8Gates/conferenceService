package com.pluralsight;

import com.pluralsight.model.Speaker;
import com.pluralsight.repository.SpeakerRepository;
import com.pluralsight.repository.SpeakerRepositoryStub;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("speaker")
public class SpeakerResource {
    private SpeakerRepository speakerRepository = new SpeakerRepositoryStub();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Speaker> findAll(){
        return speakerRepository.findAll();
    }

    @Path("{id}")
    @GET
    public Speaker getSpeaker (@PathParam("id") Long id){
        return speakerRepository.findById(id);
    }
}

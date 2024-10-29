package com.pluralsight;

import com.pluralsight.model.Speaker;
import com.pluralsight.repository.SpeakerRepository;
import com.pluralsight.repository.SpeakerRepositoryStub;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

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


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Speaker createSpeaker(Speaker speaker){
        speaker = speakerRepository.create(speaker);
        return speaker;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Speaker updateSpeaker(Speaker speaker){
        if(speakerRepository.findById(speaker.getId())==null){
            return null;
        }else{
            speaker = speakerRepository.update(speaker);
            return speaker;
        }
    }

    @Path("{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteSpeaker(@PathParam("id") Long id){
        speakerRepository.delete(id);
    }

    @DELETE
    @Path("removeall")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteAll(){
        speakerRepository.deleteAll();
    }
}

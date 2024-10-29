package com.pluralsight.repository;

import com.pluralsight.model.Speaker;

import java.util.ArrayList;
import java.util.List;

public class SpeakerRepositoryStub implements SpeakerRepository {
    private static List<Speaker> speakers = new ArrayList<>();
    public SpeakerRepositoryStub(){
        // no argument constructor for testing purposes
        /*
        Speaker speaker1 = new Speaker();
        speaker1.setId(1L);
        speaker1.setName("XNL-350");
        speaker1.setCompany("Sony");
        speakers.add(speaker1);

        Speaker speaker2 = new Speaker();
        speaker2.setId(2L);
        speaker2.setName("JBR-8090");
        speaker2.setCompany("Samsung");
        speakers.add(speaker2);
        */
    }
    @Override
    public List<Speaker> findAll(){
        return this.speakers;
    }

    @Override
    public Speaker findById(Long id) {
        return findSpeakerById(speakers, id);
    }

    @Override
    public Speaker create(Speaker speaker) {
        speaker.setId(speakers.size() + 1L);
        speakers.add(speaker);
        return speaker;
    }

    private Speaker findSpeakerById(List<Speaker> speakers, Long id) {
        return speakers.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }
}

package com.pluralsight.repository;

import com.pluralsight.model.Speaker;

import java.util.ArrayList;
import java.util.List;

public class SpeakerRepositoryStub implements SpeakerRepository {
    @Override
    public List<Speaker> findAll(){
        List<Speaker> speakers = new ArrayList<>();

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

        return speakers;
    }
}

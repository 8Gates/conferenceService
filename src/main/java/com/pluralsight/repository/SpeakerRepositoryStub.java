package com.pluralsight.repository;

import com.pluralsight.model.Speaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        speaker.setId(speakers.size()+1L);
        speakers.add(speaker);
        return speaker;
    }

    @Override
    public Speaker update(Speaker speaker) {
        Speaker storedSpeaker = findSpeakerById(speakers, speaker.getId());

        storedSpeaker.setName(speaker.getName());
        storedSpeaker.setCompany(speaker.getCompany());

        return storedSpeaker;
    }

    @Override
    public void delete(Long id) {
        Speaker speaker = findSpeakerById(speakers, id);
        speakers.remove(speaker);
    }

    @Override
    public void deleteAll() {
        speakers.clear();
    }

    @Override
    public List<Speaker> findByCompany(List<String> companies) {
        List<Speaker> speakerList = new ArrayList<>();
        for(String company:companies){
            for(Speaker speaker: speakers){
                if(Objects.equals(speaker.getCompany(), company)){
                    speakerList.add(speaker);
                }
            }
        }
        return speakerList;
    }

    private Speaker findSpeakerById(List<Speaker> speakers, Long id) {
        return speakers.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }
}

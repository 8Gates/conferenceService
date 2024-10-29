package com.pluralsight.repository;

import com.pluralsight.model.Speaker;

import java.util.List;

public interface SpeakerRepository {
    List<Speaker> findAll();

    Speaker findById(Long id);

    Speaker create(Speaker speaker);
}

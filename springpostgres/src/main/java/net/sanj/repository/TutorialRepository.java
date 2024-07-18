package net.sanj.repository;

import net.sanj.model.Tutorial;

import java.util.List;

public interface TutorialRepository {

    int save(Tutorial book);
    int update(Tutorial book);

    Tutorial findById(Long id);
    int deleteById(Long id);

    List<Tutorial> findAll();
    List<Tutorial> finddByPulished(boolean published);
    List<Tutorial> findByTitleContaining(String title);

    int deleteAllAll();
}

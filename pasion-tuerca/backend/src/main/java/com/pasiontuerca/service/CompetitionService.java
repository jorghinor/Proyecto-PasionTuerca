package com.pasiontuerca.service;

import com.pasiontuerca.model.Competition;
import com.pasiontuerca.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Optional<Competition> getCompetitionById(Long id) {
        return competitionRepository.findById(id);
    }

    public Competition createCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

    public Competition updateCompetition(Long id, Competition updatedCompetition) {
        return competitionRepository.findById(id).map(competition -> {
            competition.setTitle(updatedCompetition.getTitle());
            competition.setDescription(updatedCompetition.getDescription());
            competition.setContractorInfo(updatedCompetition.getContractorInfo());
            competition.setImageUrl(updatedCompetition.getImageUrl());
            competition.setVideoUrl(updatedCompetition.getVideoUrl());
            return competitionRepository.save(competition);
        }).orElseThrow(() -> new RuntimeException("Competition not found with id " + id));
    }

    public void deleteCompetition(Long id) {
        competitionRepository.deleteById(id);
    }
}
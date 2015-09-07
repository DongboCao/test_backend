package com.test.candidate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.candidate.persistence.entity.Candidate;
import com.test.candidate.persistence.repository.CandidateRepository;

/**
 * Created by oleg on 12/08/15.
 */
@Component
public class CandidateService {
	@Autowired
	private CandidateRepository candidateRepository;

	public List<Candidate> findAllCandidates() {
		return candidateRepository.findAll();
	}

	public Candidate getCandidate(int id) {
		return candidateRepository.getOne(id);
	}

	public Candidate saveOrUpdateCandidate(Candidate candidate) {
		return candidateRepository.saveAndFlush(candidate);
	}

	public void deleteCandidates(List<Candidate> candidates) {
		candidateRepository.deleteInBatch(candidates);
	}
}

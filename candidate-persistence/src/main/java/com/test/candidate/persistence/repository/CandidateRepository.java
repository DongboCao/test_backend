package com.test.candidate.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.candidate.persistence.entity.Candidate;

/**
 * Created by oleg on 09/08/15.
 */
public interface CandidateRepository extends JpaRepository<Candidate, Integer>{
}

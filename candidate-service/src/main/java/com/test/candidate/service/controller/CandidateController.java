package com.test.candidate.service.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.candidate.persistence.entity.Candidate;
import com.test.candidate.service.CandidateService;
import com.test.candidate.service.exception.ErrorDTO;

/**
 * Created by oleg on 12/08/15.
 */
@RestController
@RequestMapping("/candidate")
public class CandidateController {
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private MessageSendingOperations<String> messagingTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public List<Candidate> findAllCandidates(@RequestBody String name) {
		List<Candidate> ret = new ArrayList<Candidate>();
		//This code just shows how to use JDK 8 stream. in real situation, we should use where clause in persistence layer.
		candidateService.findAllCandidates().stream().filter(c->c.getName().equals(name)).forEach(ret::add);
		return ret;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public void updateCandidate(@PathVariable int id, @Valid @RequestBody Candidate candidate) {
		Candidate updateCandidate = candidateService.getCandidate(id);
		updateCandidate.setEnabled(candidate.isEnabled());
		updateCandidate.setName(candidate.getName());
		candidateService.saveOrUpdateCandidate(updateCandidate);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Candidate createCandidate(@Valid @RequestBody Candidate candidate) {
		Candidate newCandidate = candidateService.saveOrUpdateCandidate(candidate);
		messagingTemplate.convertAndSend(
	            "/app/candidate", newCandidate);
		return newCandidate;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void deleteCandidates(@RequestBody Integer[] ids) {
		List<Candidate> candidates = new ArrayList<Candidate>();
	    Arrays.asList(ids).stream().map(id->candidateService.getCandidate(id)).forEach(candidates::add);
		candidateService.deleteCandidates(candidates);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Candidate not found")
	public void notFound() {
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorDTO validationException(MethodArgumentNotValidException e) {
		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setType("InvalidRequestException");
		errorDTO.setMessage("Invalid NewCandidate");
		errorDTO.setFieldErrors(errors);
		return errorDTO;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
	public void exception() {
	}

}

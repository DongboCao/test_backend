package com.test.candidate.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NonNull;

/**
 * Created by oleg on 09/08/15.
 */
@Entity
@Table(name="candidate ")
public class Candidate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer Id;
	
	@NonNull
	@Column(length=30)
	String name;
	
	@Column
	boolean enabled;
	
	
	public Integer getId() {
		return Id;
	}
	
	public void setId(Integer id) {
		Id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

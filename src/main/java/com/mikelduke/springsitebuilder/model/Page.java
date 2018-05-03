package com.mikelduke.springsitebuilder.model;


import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pages")
@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page {

	@Id
	@GeneratedValue
	private long id;

	@NotBlank
	private String name;

	@NotBlank
	private String shortName;

	private String desc;

	@OneToMany(mappedBy="id")
	private Collection<Post> posts;

}
package com.mikelduke.charlie.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menuitems")
@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue
    private long id;
    
    private int position;

    @NotBlank
    private String text;

    @NotBlank
    private String target;

    private boolean relative;
}
package com.mikelduke.charlie.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts", uniqueConstraints=
    @UniqueConstraint(columnNames={"shortName"})
)
@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String shortName;

    @NotBlank
    @Builder.Default
    private String layout = "post";

    private String author;

    //TODO Categories and tags for links?

    @Column(length=50000)
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date date;

    @ManyToOne
    @JoinColumn(name="page_id")
    private Page page;

    public Post(Post post) {
        this.id = post.id;
        this.title = post.title;
        this.shortName = post.shortName;
        this.layout = post.layout;
        this.author = post.author;
        this.content = post.content;
        this.date = post.date;
        this.page = post.page;
    }
}
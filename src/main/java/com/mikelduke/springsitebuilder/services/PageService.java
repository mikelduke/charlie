package com.mikelduke.springsitebuilder.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.mikelduke.springsitebuilder.model.Page;
import com.mikelduke.springsitebuilder.repositories.PageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    @Value("${sssg.homepage.shortname:null}")
    String defaultPage;

    @Autowired
    PageRepository pageRepository;

    List<String> reservedNames = Arrays.asList(new String[] {
        "menu",
        "pages",
        "posts",
        "generate"
    });
    
    public Optional<Page> findOneByShortName(String shortName) {
        return pageRepository.findOneByShortName(shortName);
    }

    public org.springframework.data.domain.Page<Page> findAll(PageRequest pr) {
        return pageRepository.findAll(pr);
    }

    public Iterable<Page> findAll() {
        return pageRepository.findAll();
    }

    public long count() {
        return pageRepository.count();
    }

    public Page save(Page p) {
        if (reservedNames.contains(p.getShortName())) {
            throw new IllegalArgumentException("page shortname is reserved");
        }
        
        return pageRepository.save(p);
    }

	public Page findHomePage() {
        return pageRepository.findOneByShortName(defaultPage).orElse(
                    pageRepository.findAll(PageRequest.of(0, 1, Sort.Direction.ASC, "id"))
                    .stream().findFirst().orElse(null));
	}
}

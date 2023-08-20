package com.djordjije11.libraryappapi.service.publisher.impl;

import com.djordjije11.libraryappapi.model.Publisher;
import com.djordjije11.libraryappapi.repository.PublisherRepository;
import com.djordjije11.libraryappapi.service.GlobalTransactional;
import com.djordjije11.libraryappapi.service.publisher.PublisherService;
import com.djordjije11.libraryappapi.specification.publisher.PublisherSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@GlobalTransactional
@Service
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Page<Publisher> get(String search, Pageable pageable) {
        return publisherRepository.findAll(PublisherSpecification.bySearch(search), pageable);
    }
}

package com.djordjije11.libraryappapi.service.publisher;

import com.djordjije11.libraryappapi.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Represents the service layer for a Publisher class.
 *
 * @author Djordjije Radovic
 */
public interface PublisherService {
    /**
     * Returns the page of publishers filtered by search text and selected by options of Pageable instance.
     * The search text should contain member's idCardNumber, firstname, lastname or email.
     *
     * @param search   by publisher's name.
     * @param pageable contains options for skipping and taking database records and sorting them.
     * @return page of publishers filtered by search text and selected by options of Pageable instance.
     */
    Page<Publisher> get(String search, Pageable pageable);
}

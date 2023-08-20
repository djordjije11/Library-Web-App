package com.djordjije11.libraryappapi.mapper.publisher;

import com.djordjije11.libraryappapi.dto.publisher.PublisherShortDto;
import com.djordjije11.libraryappapi.mapper.HelperMapper;
import com.djordjije11.libraryappapi.model.Publisher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper extends HelperMapper {

    PublisherShortDto mapShort(Publisher publisher);
}

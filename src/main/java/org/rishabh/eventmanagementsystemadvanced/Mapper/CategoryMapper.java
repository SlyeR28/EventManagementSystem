package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Category;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CategoryDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.CategoryRequest;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryRequest dto);

    @Mapping(target = "eventIds", expression = "java(mapEventIds(category.getEvents()))")
    CategoryDto toDto(Category category);

    default Set<Long> mapEventIds(Set<Event> events) {
        if (events == null) return null;
        return events.stream().map(Event::getId).collect(Collectors.toSet());
    }

}

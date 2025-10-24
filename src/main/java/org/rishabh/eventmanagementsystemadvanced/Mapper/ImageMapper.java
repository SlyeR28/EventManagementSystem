package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Images;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ImageInfo;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    // Map Image entity to ImageInfo record
    ImageInfo toImageInfo(Images image);
}

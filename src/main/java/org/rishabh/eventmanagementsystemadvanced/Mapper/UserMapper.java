package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Map from UserRequestDto -> User (for saving)
    @Mapping(target = "id", ignore = true) // id is auto-generated
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "activationCode", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    User toEntity(UserRequest request);

    // Map from User -> UserResponseDto (for API response)
    UserDto toDto(User user);
}

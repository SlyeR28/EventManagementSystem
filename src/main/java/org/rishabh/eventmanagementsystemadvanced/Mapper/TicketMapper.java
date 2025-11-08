package org.rishabh.eventmanagementsystemadvanced.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Ticket;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketValidation;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ValidationResponse;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(source = "ticketType.name", target = "ticketTypeName")
    @Mapping(source = "purchaser.fullName", target = "purchaserName")
    TicketResponse toResponse(Ticket ticket);


//    @Named("mapValidations")
//    default List<ValidationResponse> mapValidations(List<TicketValidation> validations) {
//        return validations.stream()
//                .map(v -> ValidationResponse.builder()
//                        .id(v.getId())
//                        .validationStatus(v.getValidationStatus())
//                        .validationMethod(v.getValidationMethod())
//                        .createdAt(v.getCreatedAt())
//                        .build())
//                .collect(Collectors.toList());
//    }
}

package org.rishabh.eventmanagementsystemadvanced.Mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Payment;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;

//@Mapper(componentModel = "spring")
public interface PaymentMapper {

//    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
//
//    // ✅ Convert entity → response (for API output)
//    @Mapping(source = "id", target = "paymentId")
//    @Mapping(source = "order.id", target = "orderId")
//    @Mapping(source = "paymentMethod", target = "paymentMethod")
//    @Mapping(source = "paymentStatus", target = "status")
//    PaymentResponse toResponse(Payment payment);
//
//    // ✅ Convert request → entity (for creating new payment)
//    @Mapping(target = "id", ignore = true) // Auto-generated
//    @Mapping(target = "order", ignore = true) // You’ll set this manually in service
//    @Mapping(target = "paymentStatus", ignore = true) // set programmatically (PENDING/SUCCESS)
//    @Mapping(target = "transactionId", ignore = true)
//    @Mapping(target = "paymentDate", ignore = true)
//    Payment toEntity(PaymentRequest request);

}

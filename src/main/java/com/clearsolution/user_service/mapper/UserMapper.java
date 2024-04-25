package com.clearsolution.user_service.mapper;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.request.UserCreateRequest;
import com.clearsolution.user_service.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    User toEntity(UserCreateRequest registrationRequest);

    UserVM toVM(User user);
}

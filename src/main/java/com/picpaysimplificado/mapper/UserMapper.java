package com.picpaysimplificado.mapper;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(UserDto userDto);
}

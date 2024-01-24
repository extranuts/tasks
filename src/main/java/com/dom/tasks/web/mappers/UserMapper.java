package com.dom.tasks.web.mappers;

import com.dom.tasks.domain.user.User;
import com.dom.tasks.web.dto.user.UserDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
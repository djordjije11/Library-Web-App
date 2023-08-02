package com.djordjije11.libraryappapi.mapper.employee;

import com.djordjije11.libraryappapi.config.authentication.claim.EmployeeClaim;
import com.djordjije11.libraryappapi.mapper.HelperMapper;
import com.djordjije11.libraryappapi.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends HelperMapper {

    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "userProfile.id", target = "userProfileId")
    EmployeeClaim mapClaim(Employee employee);
}

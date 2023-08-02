package com.djordjije11.libraryappapi.config.authentication;

import com.djordjije11.libraryappapi.config.authentication.claim.BuildingClaim;
import com.djordjije11.libraryappapi.config.authentication.claim.EmployeeClaim;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthClaimsHolder {
    private EmployeeClaim employeeClaim;
    private BuildingClaim buildingClaim;

    public AuthClaimsHolder() {
    }

    public EmployeeClaim getEmployeeClaim() {
        return employeeClaim;
    }

    public void setEmployeeClaim(EmployeeClaim employeeClaim) {
        this.employeeClaim = employeeClaim;
    }

    public BuildingClaim getBuildingClaim() {
        return buildingClaim;
    }

    public void setBuildingClaim(BuildingClaim buildingClaim) {
        this.buildingClaim = buildingClaim;
    }
}

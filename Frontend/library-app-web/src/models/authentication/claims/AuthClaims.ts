export default interface AuthClaims {
    employeeClaim: EmployeeClaim;
    buildingClaim: BuildingClaim;
}

export interface BuildingClaim {
    id: number,
    street: string,
    city: string
}

export interface EmployeeClaim {
    id: number,
    idCardNumber: string,
    firstname: string,
    lastname: string,
    email: string,
    buildingId: number,
    userProfileId: number
}
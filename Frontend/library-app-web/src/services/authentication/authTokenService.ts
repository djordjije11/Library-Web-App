import jwtDecode from "jwt-decode";
import AuthClaims, { BuildingClaim, EmployeeClaim } from "../../models/authentication/claims/AuthClaims";

const AUTH_JWT_TOKEN_LOCAL_KEY : string = "auth-token";

//const dispatch = useAppDispatch();

export function decodeAuthToken(token: string) : AuthClaims | null {
    try{
        const claims : any = jwtDecode(token);
        return {
            employeeClaim: claims.employee as EmployeeClaim, 
            buildingClaim: claims.building as BuildingClaim
        };
    } catch(error){
        console.log(error);
        return null;
    }
}

export function saveAuthToken(token: string) {
    localStorage.setItem(AUTH_JWT_TOKEN_LOCAL_KEY, token);
}

export function removeAuthToken() {
    localStorage.removeItem(AUTH_JWT_TOKEN_LOCAL_KEY);
}

export function getAuthToken() : string | null {
    return localStorage.getItem(AUTH_JWT_TOKEN_LOCAL_KEY);
}
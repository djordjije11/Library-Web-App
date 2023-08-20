import jwtDecode from "jwt-decode";
import AuthClaims, {
  BuildingClaim,
  EmployeeClaim,
} from "../../models/authentication/claims/AuthClaims";

const AUTH_JWT_TOKEN_LOCAL_KEY: string = "auth-token";

export function getDecodedAuthToken(): AuthClaims | null {
  const authToken = getAuthToken();
  if (authToken === null) {
    return null;
  }
  return decodeAuthToken(authToken);
}

export function decodeAndSaveAuthToken(token: string): AuthClaims | null {
  const authClaims = decodeAuthToken(token);
  if (authClaims !== null) {
    saveAuthToken(token);
  }
  return authClaims;
}

export function getAuthToken(): string | null {
  return localStorage.getItem(AUTH_JWT_TOKEN_LOCAL_KEY);
}

export function removeAuthToken() {
  localStorage.removeItem(AUTH_JWT_TOKEN_LOCAL_KEY);
}

function decodeAuthToken(token: string): AuthClaims | null {
  try {
    const claims: any = jwtDecode(token);
    if (Date.now() >= claims.exp * 1000) {
      removeAuthToken();
      return null;
    }
    return {
      employeeClaim: claims.employee as EmployeeClaim,
      buildingClaim: claims.building as BuildingClaim,
    };
  } catch (error) {
    return null;
  }
}

function saveAuthToken(token: string) {
  localStorage.setItem(AUTH_JWT_TOKEN_LOCAL_KEY, token);
}

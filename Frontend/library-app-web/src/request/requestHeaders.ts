import { getAuthToken } from "../services/authentication/authTokenService";

export interface AuthorizationHeader {
  Authorization: string;
}

export function getAuthorizationHeader(): AuthorizationHeader {
  const token = getAuthToken();
  if (token === null) {
    return { Authorization: "" };
  }
  return { Authorization: `Bearer ${token}` };
}

export function getHeaders(): any {
  return {
    "Content-Type": "application/json",
    ...getAuthorizationHeader(),
  };
}

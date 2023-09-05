import { getAuthToken } from "../services/authentication/authTokenService";

const HEADER_PAGINATION_TOTAL_PAGES = "x-pagination-total-pages";
const HEADER_PAGINATION_TOTAL_ENTRIES = "x-pagination-total-entries";

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

export function extractTotalPagesFromHeaders(headers: any): number {
  return Number(headers[HEADER_PAGINATION_TOTAL_PAGES]);
}

export function extractTotalItemsCountFromHeaders(headers: any): number {
  return Number(headers[HEADER_PAGINATION_TOTAL_ENTRIES]);
}

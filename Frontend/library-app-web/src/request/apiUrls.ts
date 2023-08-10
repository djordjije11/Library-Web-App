const SERVER_DOMAIN = "http://localhost";
const SERVER_PORT = "8080";
const SERVER_API_BASE = "api";

function getUrl(path: string): string {
  return `${SERVER_DOMAIN}:${SERVER_PORT}/${SERVER_API_BASE}/${path}`;
}

const LOGIN = "auth/login";
const MEMBER = "member";

export const LOGIN_URL = getUrl(LOGIN);
export const GET_MEMBERS_URL = getUrl(MEMBER);
export const ADD_MEMBER_URL = getUrl(MEMBER);

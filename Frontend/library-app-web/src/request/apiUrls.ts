const SERVER_DOMAIN = "http://localhost";
const SERVER_PORT = "8080";
const SERVER_API_BASE = "api";

function getUrl(path: string): string {
  return `${SERVER_DOMAIN}:${SERVER_PORT}/${SERVER_API_BASE}/${path}`;
}

const LOGIN = "auth/login";
const MEMBER = "member";
const LENDING = "lending";
const BOOK = "book";
const BOOK_COPY = "book-copy";

export const LOGIN_URL = getUrl(LOGIN);

export const GET_MEMBERS_URL = getUrl(MEMBER);
export const GET_MEMBER_URL = (id: number) => `${getUrl(MEMBER)}/${id}`;
export const ADD_MEMBER_URL = getUrl(MEMBER);
export const UPDATE_MEMBER_URL = (id: number) => `${getUrl(MEMBER)}/${id}`;
export const DELETE_MEMBER_URL = (id: number) => `${getUrl(MEMBER)}/${id}`;

export const GET_ALL_BOOKS_COPIES_AVAILABLE_IN_BUILDING = `${getUrl(
  BOOK
)}/all/${BOOK_COPY}`;

export const ADD_LENDINGS_URL = `${getUrl(LENDING)}/create`;

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
const PUBLISHER = "publisher";
const AUTHOR = "author";

export const LOGIN_URL = getUrl(LOGIN);

export const GET_MEMBERS_URL = getUrl(MEMBER);
export const GET_MEMBER_URL = (id: number) => getUrl(`${MEMBER}/${id}`);
export const ADD_MEMBER_URL = getUrl(MEMBER);
export const UPDATE_MEMBER_URL = (id: number) => getUrl(`${MEMBER}/${id}`);
export const DELETE_MEMBER_URL = (id: number) => getUrl(`${MEMBER}/${id}`);

export const GET_BOOKS_URL = getUrl(BOOK);
export const GET_BOOK_URL = (id: number) => getUrl(`${BOOK}/${id}`);
export const DELETE_BOOK_URL = (id: number) => getUrl(`${BOOK}/${id}`);
export const ADD_BOOK_URL = getUrl(BOOK);
export const UPDATE_BOOK_URL = (id: number) => getUrl(`${BOOK}/${id}`);

export const GET_BOOK_COPIES_IN_ALL_BUILDINGS_URL = (bookId: number) =>
  getUrl(`${BOOK}/${bookId}/${BOOK_COPY}/all-buildings`);
export const GET_ALL_BOOKS_COPIES_AVAILABLE_IN_BUILDING = getUrl(
  `${BOOK}/all/${BOOK_COPY}`
);
export const ADD_BOOK_COPY_URL = (bookId: number) =>
  getUrl(`${BOOK}/${bookId}/${BOOK_COPY}`);
export const UPDATE_BOOK_COPY_URL = (bookId: number, bookCopyId: number) =>
  getUrl(`${BOOK}/${bookId}/${BOOK_COPY}/${bookCopyId}`);
export const DISCARD_BOOK_COPY_URL = (bookId: number, bookCopyId: number) =>
  getUrl(`${BOOK}/${bookId}/${BOOK_COPY}/${bookCopyId}/discard`);

export const GET_PUBLISHERS_URL = getUrl(PUBLISHER);

export const GET_AUTHORS_URL = getUrl(AUTHOR);

export const ADD_LENDINGS_URL = getUrl(`${LENDING}/create`);
export const RETURN_LENDINGS_URL = getUrl(`${LENDING}/return`);
export const GET_UNRETURNED_LENDINGS_BY_MEMBER = (memberId: number) =>
  getUrl(`${LENDING}/${MEMBER}/${memberId}/unreturned`);
export const GET_LENDINGS_BY_MEMBER = (memberId: number) =>
  getUrl(`${LENDING}/${MEMBER}/${memberId}`);

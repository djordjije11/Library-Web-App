import { Fragment } from "react";
import "../../App.css";
import LoginPage from "../login/LoginPage";
import HomePage from "../home/HomePage";
import { Route, Routes } from "react-router-dom";
import AuthenticatedRoute from "./AuthenticatedRoute";
import UnauthenticatedRoute from "./UnauthenticatedRoute";
import UnspecifiedRoute from "./UnspecifiedRoute";
import MemberAddPage from "../member/MemberAddPage";
import MemberListPage from "../member/MemberListPage";
import LendingAddPage from "../lending/LendingAddPage";
import TestPage from "../TestPage";
import LendingReturnPage from "../lending/LendingReturnPage";
import BookListPage from "../book/BookListPage";
import { BookCopiesListPage } from "../bookcopy/BookCopiesListPage";

export const LOGIN_PAGE = "/login";
export const HOME_PAGE = "/";
export const ADD_MEMBER_PAGE = "/member/add";
export const LIST_MEMBER_PAGE = "/member/list";
export const LIST_BOOK_PAGE = "/book";
export const LIST_COPIES_OF_BOOK_PAGE = "/book/:id/copy";
export const get_LIST_COPIES_OF_BOOK_PAGE = (bookId: number) =>
  `/book/${bookId}/copy`;
export const ADD_LENDING_PAGE = "/lending/add";
export const RETURN_LENDING_PAGE = "/lending/return";
export const UNSPECIFIED_LINK = "/*";

export default function AppRouter() {
  return (
    <Routes>
      <Fragment>
        <Route path="/test" element={<TestPage />} />
        <Route
          path={HOME_PAGE}
          element={
            <AuthenticatedRoute>
              <HomePage />
            </AuthenticatedRoute>
          }
        />
        <Route
          path={LOGIN_PAGE}
          element={
            <UnauthenticatedRoute>
              <LoginPage />
            </UnauthenticatedRoute>
          }
        />
        <Route
          path={ADD_MEMBER_PAGE}
          element={
            <AuthenticatedRoute>
              <MemberAddPage />
            </AuthenticatedRoute>
          }
        />
        <Route
          path={LIST_MEMBER_PAGE}
          element={
            <AuthenticatedRoute>
              <MemberListPage />
            </AuthenticatedRoute>
          }
        />
        <Route
          path={LIST_BOOK_PAGE}
          element={
            <AuthenticatedRoute>
              <BookListPage />
            </AuthenticatedRoute>
          }
        />
        <Route
          path={LIST_COPIES_OF_BOOK_PAGE}
          element={
            <AuthenticatedRoute>
              <BookCopiesListPage />
            </AuthenticatedRoute>
          }
        />
        <Route
          path={ADD_LENDING_PAGE}
          element={
            <AuthenticatedRoute>
              <LendingAddPage />
            </AuthenticatedRoute>
          }
        />
        <Route
          path={RETURN_LENDING_PAGE}
          element={
            <AuthenticatedRoute>
              <LendingReturnPage />
            </AuthenticatedRoute>
          }
        />
        <Route path={UNSPECIFIED_LINK} element={<UnspecifiedRoute />} />
      </Fragment>
    </Routes>
  );
}

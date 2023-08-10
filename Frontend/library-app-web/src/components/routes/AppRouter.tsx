import { Fragment } from "react";
import "../../App.css";
import LoginPage from "../login/LoginPage";
import HomePage from "../home/HomePage";
import { Route, Routes } from "react-router-dom";
import AuthenticatedRoute from "./AuthenticatedRoute";
import UnauthenticatedRoute from "./UnauthenticatedRoute";
import UnspecifiedRoute from "./UnspecifiedRoute";
import MemberAddPage from "../member/MemberAddPage";

export const LOGIN_PAGE = "/login";
export const HOME_PAGE = "/";
export const ADD_MEMBER_PAGE = "/member";
export const UNSPECIFIED_LINK = "/*";

export default function AppRouter() {
  return (
    <Routes>
      <Fragment>
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
        <Route path={UNSPECIFIED_LINK} element={<UnspecifiedRoute />} />
      </Fragment>
    </Routes>
  );
}

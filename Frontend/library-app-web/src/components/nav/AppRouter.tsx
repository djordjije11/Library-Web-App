import { Fragment } from "react";
import "../../App.css";
import LoginPage from "../../pages/LoginPage";
import HomePage from "../../pages/HomePage";
import { Route, Routes } from "react-router-dom";
import {
  ADD_MEMBER_PAGE,
  HOME_PAGE,
  LOGIN_PAGE,
  UNSPECIFIED_LINK,
} from "../../components/nav/routesUrls";
import AuthenticatedRoute from "../../components/nav/AuthenticatedRoute";
import UnauthenticatedRoute from "../../components/nav/UnauthenticatedRoute";
import UnspecifiedRoute from "../../components/nav/UnspecifiedRoute";

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
              <div>HELLO</div>
            </AuthenticatedRoute>
          }
        />
        <Route path={UNSPECIFIED_LINK} element={<UnspecifiedRoute />} />
      </Fragment>
    </Routes>
  );
}

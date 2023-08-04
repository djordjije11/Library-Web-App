import { useEffect, useState } from "react";
import "./App.css";
import LoginPage from "./pages/LoginPage";
import { getAuthToken } from "./services/authentication/authTokenService";
import { useAppDispatch, useAppSelector } from "./store/config/hooks";
import { authActions } from "./store/authentication/authSlice";
import HomePage from "./pages/HomePage";
import Loader from "./components/shared/Loader";
import { loaderActions } from "./store/loader/loaderSlice";

function App() {
  // const [loading, setLoading] = useState<boolean>();
  const userLoggedIn: boolean = useAppSelector((state) => state.auth.loggedIn);
  const dispatch = useAppDispatch();
  const [checked, setChecked] = useState(false);

  function setUpLogin() {
    // setLoading(true);
    dispatch(loaderActions.show(true));
    const authToken = getAuthToken();
    if (authToken !== null) {
      dispatch(authActions.loggedIn(authToken));
    }
    // setLoading(false);
    setChecked(true);
    dispatch(loaderActions.show(false));
  }

  useEffect(setUpLogin, []);

  // if (loading) {
  //   return <Loader />;
  // }

  if (checked === false) {
    return <></>;
  }

  return userLoggedIn ? <HomePage /> : <LoginPage />;
}

export default App;

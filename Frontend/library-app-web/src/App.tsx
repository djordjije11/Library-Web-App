import { useEffect, useState } from "react";
import "./App.css";
import LoginPage from "./pages/LoginPage";
import { useAppDispatch, useAppSelector } from "./store/config/hooks";
import HomePage from "./pages/HomePage";
import { loaderActions } from "./store/loader/loaderSlice";
import { loginThunk } from "./store/authentication/authThunks";

function App() {
  // const [loading, setLoading] = useState<boolean>();
  const userLoggedIn: boolean = useAppSelector((state) => state.auth.loggedIn);
  const dispatch = useAppDispatch();
  const [checked, setChecked] = useState(false);

  function setUpLogin() {
    // setLoading(true);
    dispatch(loaderActions.show(true));
    dispatch(loginThunk());
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

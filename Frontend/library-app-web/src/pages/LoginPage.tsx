import { FormEvent, useState } from "react";
import { useAppDispatch } from "../store/config/hooks";
import { loginAsyncThunk } from "../store/authentication/authThunks";
import LoginInput from "../models/authentication/LoginInput";
import Alert from "../components/Alert";
import { useNavigate, useLocation } from "react-router-dom";
import { HOME_PAGE } from "../components/nav/routesUrls";
import { LocationState } from "../components/nav/AuthenticatedRoute";

export default function LoginPage() {
  const [loginInput, setLoginInput] = useState<LoginInput>({
    username: "",
    password: "",
  });
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const location = useLocation();

  function navigateOnLogin() {
    const locationState = location.state as LocationState;
    if (locationState.from === undefined) {
      navigate(HOME_PAGE);
    }
    navigate(locationState.from);
  }

  async function handleLoginAsync(event: FormEvent) {
    event.preventDefault();
    try {
      await dispatch(loginAsyncThunk(loginInput));
      navigateOnLogin();
    } catch (error) {
      console.log(error);
    }
  }

  function handlePropertyChange(event: React.ChangeEvent<HTMLInputElement>) {
    setLoginInput((prevUserInput) => {
      return {
        ...prevUserInput,
        [event.target.name]: event.target.value,
      } as LoginInput;
    });
  }

  return (
    <>
      <Alert />
      <form onSubmit={handleLoginAsync}>
        <input
          type="text"
          name="username"
          value={loginInput.username}
          onChange={handlePropertyChange}
        />
        <input
          type="password"
          name="password"
          value={loginInput.password}
          onChange={handlePropertyChange}
        />
        <button type="submit">LOGIN</button>
      </form>
    </>
  );
}

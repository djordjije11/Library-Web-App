import { FormEvent, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import { loginAsyncThunk } from "../../store/authentication/authThunks";
import LoginInput, {
  LoginInputResults,
} from "../../models/authentication/LoginInput";
import { useNavigate, useLocation } from "react-router-dom";
import { HOME_PAGE } from "../routes/AppRouter";
import { LocationState } from "../routes/AuthenticatedRoute";
import { Card, Input, Button, Typography } from "@material-tailwind/react";
import FormField from "../form/FormField";
import {
  validatePassword,
  validateUsername,
} from "../../validation/modelValidations";
import { allValid } from "../../models/validation/ValidationResult";
import AuthState from "../../store/authentication/AuthState";

export default function LoginForm() {
  const authState: AuthState = useAppSelector((state) => state.auth);
  const [loginInput, setLoginInput] = useState<LoginInput>({} as LoginInput);
  const [loginInputResults, setLoginInputResults] = useState<LoginInputResults>(
    {} as LoginInputResults
  );
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

  function validateForm(): boolean {
    const usernameResult = validateUsername(loginInput.username);
    const passwordResult = validatePassword(loginInput.password);
    setLoginInputResults((prev) => {
      return { ...prev, usernameResult, passwordResult };
    });
    return allValid(usernameResult, passwordResult);
  }

  async function handleLoginAsync(event: FormEvent) {
    event.preventDefault();
    const formValid = validateForm();
    if (formValid === false) {
      return;
    }
    await dispatch(loginAsyncThunk(loginInput));
    console.log("HEJ");
    console.log(authState.isError);
    if (authState.isError) {
      console.log(authState.error);
    }
    navigateOnLogin();
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
    <Card color="transparent" shadow={false}>
      <Typography variant="h4" color="blue-gray">
        Log in
      </Typography>
      <Typography color="gray" className="mt-1 font-normal">
        Enter your details to log in.
      </Typography>
      <form
        className="mt-3 mb-2 w-80 max-w-screen-lg sm:w-96"
        onSubmit={handleLoginAsync}
      >
        <div className="mb-4 flex flex-col gap-1">
          <FormField name="username" result={loginInputResults.usernameResult}>
            <Input
              size="lg"
              label="Username"
              name="username"
              value={loginInput.username || ""}
              onChange={handlePropertyChange}
            />
          </FormField>
          <FormField name="password" result={loginInputResults.passwordResult}>
            <Input
              type="password"
              size="lg"
              label="Password"
              name="password"
              value={loginInput.password || ""}
              onChange={handlePropertyChange}
            />
          </FormField>
        </div>
        <Button className="mt-6" fullWidth type="submit">
          Log in
        </Button>
      </form>
    </Card>
  );
}

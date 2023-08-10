import { FormEvent, useState } from "react";
import { useAppDispatch } from "../../store/config/hooks";
import { loginAsyncThunk } from "../../store/authentication/authThunks";
import LoginInput from "../../models/authentication/LoginInput";
import { useNavigate, useLocation } from "react-router-dom";
import { HOME_PAGE } from "../routes/AppRouter";
import { LocationState } from "../routes/AuthenticatedRoute";
import { Card, Input, Button, Typography } from "@material-tailwind/react";

export default function LoginForm() {
  const [loginInput, setLoginInput] = useState<LoginInput>({} as LoginInput);
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
    <Card color="transparent" shadow={false}>
      <Typography variant="h4" color="blue-gray">
        Log in
      </Typography>
      <Typography color="gray" className="mt-1 font-normal">
        Enter your details to log in.
      </Typography>
      <form
        className="mt-8 mb-2 w-80 max-w-screen-lg sm:w-96"
        onSubmit={handleLoginAsync}
      >
        <div className="mb-4 flex flex-col gap-6">
          <Input
            size="lg"
            label="Username"
            name="username"
            value={loginInput.username || ""}
            onChange={handlePropertyChange}
          />
          <Input
            type="password"
            size="lg"
            label="Password"
            name="password"
            value={loginInput.password || ""}
            onChange={handlePropertyChange}
          />
        </div>
        <Button className="mt-6" fullWidth type="submit">
          Log in
        </Button>
      </form>
    </Card>
  );
}

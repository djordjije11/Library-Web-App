import { FormEvent, useState } from "react";
import { useAppDispatch } from "../store/config/hooks";
import { loginAsyncThunk } from "../store/authentication/authThunks";
import LoginInput from "../models/authentication/LoginInput";
import Alert from "../components/Alert";

export default function LoginPage() {
  const [loginInput, setLoginInput] = useState<LoginInput>({
    username: "",
    password: "",
  });
  const dispatch = useAppDispatch();

  async function handleLoginAsync(event: FormEvent) {
    event.preventDefault();
    try {
      await dispatch(loginAsyncThunk(loginInput));
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

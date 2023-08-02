import { FormEvent, useEffect, useState } from "react";
import jwt from "jwt-decode";
import jwtDecode from "jwt-decode";

interface UserInput {
  username: string;
  password: string;
}

export default function Login(props: any) {
  const { loggedIn, setLoggedIn, user, setUser } = props;

  const [userInput, setUserInput] = useState<UserInput>({
    username: "",
    password: "",
  });

  async function loginAsync(event: FormEvent) {
    event.preventDefault();
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userInput),
    };

    console.log(requestOptions.body);

    const response = await fetch(
      "http://localhost:8080/api/auth/login",
      requestOptions
    );
    console.log(response);
    const responseJson = await response.json();
    console.log(responseJson);
    if (response.ok === false) {
      return;
    }
    const token = responseJson.token;
    localStorage.setItem("token", token);
    setLoggedIn(true);
    const user = jwtDecode(token);
    console.log(user);
    // setUser();
  }

  function handlePropertyChange(event: React.ChangeEvent<HTMLInputElement>) {
    setUserInput((prevUserInput) => {
      return {
        ...prevUserInput,
        [event.target.name]: event.target.value,
      } as UserInput;
    });
  }

  return (
    <form onSubmit={loginAsync}>
      <input
        type="text"
        name="username"
        value={userInput?.username}
        onChange={handlePropertyChange}
      />
      <input
        type="text"
        name="password"
        value={userInput?.password}
        onChange={handlePropertyChange}
      />
      <button type="submit">LOGIN</button>
    </form>
  );
}

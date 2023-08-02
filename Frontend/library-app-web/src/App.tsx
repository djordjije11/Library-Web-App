import { ReactElement, useEffect, useState } from "react";
import "./App.css";
import Login from "./Login";
import { JsxElement } from "typescript";
import Members from "./Members";
import jwtDecode from "jwt-decode";

interface User {
  id: number;
  idCardNumber: string;
  firstname: string;
  lastname: string;
  email: string;
  buildingId: number;
  userProfile: number;
}

function App() {
  const [loggedIn, setLoggedIn] = useState<boolean>(false);
  const [user, setUser] = useState<User>();

  useEffect(() => {
    const token: string | null = localStorage.getItem("token");
    if (token !== null) {
      setLoggedIn(true);
    }
  }, []);

  function render(): ReactElement {
    if (loggedIn === false) {
      return (
        <Login
          loggedIn={loggedIn}
          setLoggedIn={setLoggedIn}
          user={user}
          setUser={setUser}
        />
      );
    }
    return (
      <>
        <Members />
        <button
          onClick={() => {
            localStorage.removeItem("token");
            setLoggedIn(false);
          }}
        >
          LOGOUT
        </button>
      </>
    );
  }

  return render();
}

export default App;

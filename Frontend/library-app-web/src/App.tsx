import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [members, setMembers]: any[] = useState([]);

  function displayMembers(): any {
    if (members == null || members.length == 0) {
      return (
        <tr>
          <td>No data in database.</td>
        </tr>
      );
    }
    return members.map((member: any) => {
      return (
        <tr key={member.id}>
          <th scope="row">{member.id}</th>
          <td>{member.idCardNumber}</td>
          <td>{member.firstname + " " + member.lastname}</td>
          <td>{member.email}</td>
        </tr>
      );
    });
  }

  async function callMe() {
    const member: any = {
      idCardNumber: "8474652250",
      firstname: "Djordjije",
      lastname: "Radovic",
      gender: "MALE",
      email: "djordjodjolone@gmail.com",
    };
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(member),
    };

    const response = await fetch(
      "http://localhost:8080/api/member",
      requestOptions
    );
    console.log(response);
    console.log(await response.json());

    await loadData();
  }

  async function loadData() {
    const response: any = await fetch("http://localhost:8080/api/member");
    const responseJson: any = await response.json();
    console.log(responseJson);
    setMembers(responseJson);
  }

  useEffect(() => {
    loadData();
  }, []);

  return (
    <>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">ID Card</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
          </tr>
        </thead>
        <tbody>{displayMembers()}</tbody>
      </table>
      <button onClick={callMe}>CLICK ME</button>
    </>
  );
}

export default App;

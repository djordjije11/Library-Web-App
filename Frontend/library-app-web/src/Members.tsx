import axios from "axios";
import { useEffect, useState } from "react";

export default function Members() {
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
      idCardNumber: "8474252250",
      firstname: "Djordjije",
      lastname: "Radovic",
      gender: "MALE",
      email: "djordjodjolone@gmail.com",
    };
    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
      body: JSON.stringify(member),
    };

    const response = await fetch(
      "http://localhost:8080/api/member",
      requestOptions
    );
    console.log(response);
    const responseJson = await response.json();
    console.log(responseJson);

    await loadData();
  }

  async function loadData() {
    const token: string | null = localStorage.getItem("token");
    console.log("TOKEN : " + token);
    const requestOptions: RequestInit = {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    };
    console.log(requestOptions);
    const response: any = await fetch(
      "http://localhost:8080/api/member?page_size=10&page_number=2",
      requestOptions
    );
    const responseJson = await response.json();
    setMembers(responseJson);
    // const headers = {
    //   "Content-Type": "application/json",
    //   Authorization: `Bearer ${token}`,
    // };
    // console.log("HEADERS " + headers);
    // const response: any = await axios.get("http://localhost:8080/api/member", {
    //   headers,
    // });
    // console.log(response);
    //setMembers(response.data);
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

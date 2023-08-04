import { useEffect, useState } from "react";
import Member from "../models/member/Member";
import { getMembersAsync } from "../request/member/memberRequests";
import Loader from "./shared/Loader";
import { loaderActions } from "../store/loader/loaderSlice";
import { useAppDispatch } from "../store/config/hooks";

export default function Members() {
  const [members, setMembers] = useState<Member[]>([]);
  // const [loading, setLoading] = useState<boolean>();
  const dispatch = useAppDispatch();

  function displayMembers(): any {
    if (members === null || members.length === 0) {
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

  async function loadMembersAsync() {
    // setLoading(true);
    dispatch(loaderActions.show(true));
    setTimeout(async () => {
      try {
        //const members = await getMembersAsync();
        //setMembers(members);
      } catch (error) {
      } finally {
        //setLoading(false);
        dispatch(loaderActions.show(false));
      }
    }, 0);
  }

  useEffect(() => {
    loadMembersAsync();
  }, []);

  // if (loading) {
  //   return <Loader />;
  // }

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
    </>
  );
}

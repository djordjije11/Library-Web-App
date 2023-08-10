import axios from "axios";
import Member from "../../models/member/Member";
import { ADD_MEMBER_URL, GET_MEMBERS_URL } from "../apiUrls";
import { getHeaders } from "../requestHeaders";

export async function getMembersAsync(): Promise<Member[]> {
  const response = await axios.get(GET_MEMBERS_URL + "?page_size=50", {
    headers: getHeaders(),
  });
  return response.data as Member[];
}

export async function addMemberAsync(member: Member): Promise<Member> {
  const response = await axios.post(ADD_MEMBER_URL, member, {
    headers: getHeaders(),
  });
  return response.data as Member;
}

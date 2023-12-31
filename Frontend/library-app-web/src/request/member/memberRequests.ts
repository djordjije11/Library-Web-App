import axios from "axios";
import {
  ADD_MEMBER_URL,
  DELETE_MEMBER_URL,
  GET_MEMBERS_URL,
  GET_MEMBER_URL,
  UPDATE_MEMBER_URL,
} from "../apiUrls";
import {
  extractTotalItemsCountFromHeaders,
  extractTotalPagesFromHeaders,
  getHeaders,
} from "../requestHeaders";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import MemberAdd from "../../models/member/MemberAdd";
import MemberDetail from "../../models/member/MemberDetail";
import MemberShort from "../../models/member/MemberShort";

export async function getMembersAsync(
  requestQueryParams: RequestQueryParams
): Promise<{
  members: MemberShort[];
  totalPages: number;
  totalItemsCount: number;
}> {
  const response = await axios.get(
    GET_MEMBERS_URL + constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  const members = response.data as MemberShort[];
  const totalPages: number = extractTotalPagesFromHeaders(response.headers);
  console.log(response.headers);
  const totalItemsCount: number = extractTotalItemsCountFromHeaders(
    response.headers
  );
  return { members, totalPages, totalItemsCount };
}

export async function getMemberAsync(id: number): Promise<MemberDetail> {
  const response = await axios.get(GET_MEMBER_URL(id), {
    headers: getHeaders(),
  });
  return response.data as MemberDetail;
}

export async function addMemberAsync(member: MemberAdd): Promise<MemberDetail> {
  const response = await axios.post(ADD_MEMBER_URL, member, {
    headers: getHeaders(),
  });
  return response.data as MemberDetail;
}

export async function updateMemberAsync(
  member: MemberDetail
): Promise<MemberDetail> {
  const response = await axios.put(UPDATE_MEMBER_URL(member.id), member, {
    headers: getHeaders(),
  });
  return response.data as MemberDetail;
}

export async function deleteMemberAsync(id: number): Promise<void> {
  await axios.delete(DELETE_MEMBER_URL(id), { headers: getHeaders() });
}

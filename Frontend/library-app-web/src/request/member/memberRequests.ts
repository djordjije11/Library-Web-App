import axios from "axios";
import {
  ADD_MEMBER_URL,
  DELETE_MEMBER_URL,
  GET_MEMBERS_URL,
  GET_MEMBER_URL,
  UPDATE_MEMBER_URL,
} from "../apiUrls";
import { getHeaders } from "../requestHeaders";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import MemberAdd from "../../models/member/MemberAdd";
import MemberDetail from "../../models/member/MemberDetail";
import MemberShort from "../../models/member/MemberShort";

const HEADER_PAGINATION_TOTAL_PAGES = "x-pagination-total-pages";

export async function getMembersAsync(
  requestQueryParams: RequestQueryParams
): Promise<{ members: MemberShort[]; totalPages: number }> {
  const response = await axios.get(
    GET_MEMBERS_URL + constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  const members = response.data as MemberShort[];
  const totalPages: number = Number(
    response.headers[HEADER_PAGINATION_TOTAL_PAGES]
  );
  return { members, totalPages };
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

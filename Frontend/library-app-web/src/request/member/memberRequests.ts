import axios from "axios";
import Member from "../../models/member/Member";
import { ADD_MEMBER_URL, GET_MEMBERS_URL } from "../apiUrls";
import { getHeaders } from "../requestHeaders";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";

const HEADER_PAGINATION_TOTAL_PAGES = "x-pagination-total-pages";

export async function getMembersAsync(
  requestQueryParams: RequestQueryParams
): Promise<{ members: Member[]; totalPages: number }> {
  const response = await axios.get(
    GET_MEMBERS_URL + constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  const members = response.data as Member[];
  const totalPages: number = Number(
    response.headers[HEADER_PAGINATION_TOTAL_PAGES]
  );
  return { members, totalPages };
}

export async function addMemberAsync(member: Member): Promise<Member> {
  const response = await axios.post(ADD_MEMBER_URL, member, {
    headers: getHeaders(),
  });
  return response.data as Member;
}

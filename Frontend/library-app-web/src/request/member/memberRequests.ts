import axios from "axios";
import Member from "../../models/member/Member";
import { GET_MEMBERS_URL } from "../apiUrls";
import { getHeaders } from "../requestHeaders";

export async function getMembersAsync() : Promise<Member[]> {
    const response = await axios.get(GET_MEMBERS_URL, { headers: getHeaders() });
    return response.data as Member[];
}
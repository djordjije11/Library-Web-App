import axios from "axios";
import AuthorShort from "../../models/author/AuthorShort";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import { GET_AUTHORS_URL } from "../apiUrls";
import { extractTotalPagesFromHeaders, getHeaders } from "../requestHeaders";

export async function getAuthorsAsync(
  requestQueryParams: RequestQueryParams
): Promise<{ authors: AuthorShort[]; totalPages: number }> {
  const response = await axios.get(
    GET_AUTHORS_URL + constructRequestQuery(requestQueryParams),
    { headers: getHeaders() }
  );
  const authors = response.data as AuthorShort[];
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  return { authors, totalPages };
}

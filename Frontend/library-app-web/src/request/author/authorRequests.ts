import axios from "axios";
import AuthorShort from "../../models/author/AuthorShort";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import { GET_AUTHORS_URL } from "../apiUrls";
import {
  extractTotalItemsCountFromHeaders,
  extractTotalPagesFromHeaders,
  getHeaders,
} from "../requestHeaders";

export async function getAuthorsAsync(
  requestQueryParams: RequestQueryParams
): Promise<{
  authors: AuthorShort[];
  totalPages: number;
  totalItemsCount: number;
}> {
  const response = await axios.get(
    GET_AUTHORS_URL + constructRequestQuery(requestQueryParams),
    { headers: getHeaders() }
  );
  const authors = response.data as AuthorShort[];
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  const totalItemsCount = extractTotalItemsCountFromHeaders(response.headers);
  return { authors, totalPages, totalItemsCount };
}

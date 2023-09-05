import axios from "axios";
import PublisherShort from "../../models/publisher/PublisherShort";
import RequestQueryParams, {
  constructRequestQuery,
} from "../../models/request/RequestQueryParams";
import { GET_PUBLISHERS_URL } from "../apiUrls";
import {
  extractTotalItemsCountFromHeaders,
  extractTotalPagesFromHeaders,
  getHeaders,
} from "../requestHeaders";

export async function getPublishersAsync(
  requestQueryParams: RequestQueryParams
): Promise<{
  publishers: PublisherShort[];
  totalPages: number;
  totalItemsCount: number;
}> {
  const response = await axios.get(
    GET_PUBLISHERS_URL + constructRequestQuery(requestQueryParams),
    {
      headers: getHeaders(),
    }
  );
  const publishers = response.data as PublisherShort[];
  const totalPages = extractTotalPagesFromHeaders(response.headers);
  const totalItemsCount = extractTotalItemsCountFromHeaders(response.headers);
  return { publishers, totalPages, totalItemsCount };
}

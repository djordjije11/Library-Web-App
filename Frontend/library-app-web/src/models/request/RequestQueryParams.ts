import { checkNotBlank } from "../../validation/modelValidations";
import Pageable, { constructPageableQuery } from "./Pageable";
import { SortByColumn, constructSortByQuery } from "./SortBy";

export default interface RequestQueryParams {
  pageable?: Pageable;
  sortBy?: SortByColumn[];
  search?: string;
}

export function constructRequestQuery(
  requestQueryParams: RequestQueryParams
): string {
  const queries: string[] = [];
  if (requestQueryParams.pageable !== undefined) {
    queries.push(constructPageableQuery(requestQueryParams.pageable));
  }
  if (requestQueryParams.sortBy !== undefined) {
    const sortByQuery = constructSortByQuery(requestQueryParams.sortBy);
    if (sortByQuery !== null) {
      queries.push(sortByQuery);
    }
  }
  if (checkNotBlank(requestQueryParams.search)) {
    if (requestQueryParams.search !== undefined) {
      queries.push(`search=${encodeURI(requestQueryParams.search)}`);
    }
  }
  return "?" + queries.join("&");
}

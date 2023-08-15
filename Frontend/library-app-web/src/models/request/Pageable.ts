export default interface Pageable {
  pageNumber?: number;
  pageSize?: number;
}

export function constructPageableQuery(pageable: Pageable) {
  const queries: string[] = [];
  if (pageable.pageNumber !== undefined) {
    queries.push(`page_number=${pageable.pageNumber}`);
  }
  if (pageable.pageSize !== undefined) {
    queries.push(`page_size=${pageable.pageSize}`);
  }
  return queries.join("&");
}

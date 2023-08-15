export interface SortByColumn {
  desc?: boolean;
  id?: string;
}

export function constructSortByQuery(
  sortByColumns: SortByColumn[]
): string | null {
  if (sortByColumns === undefined || sortByColumns.length === 0) {
    return null;
  }
  const queries: string[] = [];
  sortByColumns.map((sortBy) => {
    const sortByColumnQuery = constructOneSortByColumnQuery(sortBy);
    if (sortByColumnQuery === null) {
      return;
    }
    queries.push(sortByColumnQuery);
  });
  return `sort_by=${queries.join(",")}`;
}

function constructOneSortByColumnQuery(
  sortByColumn: SortByColumn
): string | null {
  if (sortByColumn.id === undefined || sortByColumn.desc === undefined) {
    return null;
  }
  const sortColumn = sortByColumn.id;
  const sortDirection: string = sortByColumn.desc ? "desc" : "asc";
  return `${sortDirection}(${sortColumn})`;
}

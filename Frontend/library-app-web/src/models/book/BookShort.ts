export interface BookShortFromServer {
  id: number;
  title: string;
  publisher: string;
  authors: string;
  pagesNumber: number;
}

export interface BookShort {
  id: number;
  title: string;
  publisher: { name: string };
  authors: string;
  pagesNumber: number;
}

export function constructBookShort(book: BookShortFromServer): BookShort {
  return {
    ...book,
    authors: formatAuthors(book.authors),
    publisher: { name: book.publisher },
  };
}

export function constructBookShortArray(
  books: BookShortFromServer[]
): BookShort[] {
  return books.map((book) => constructBookShort(book));
}

function formatAuthors(authors: string): string {
  const authorsArray: string[] = authors.split(";", 4);
  if (authorsArray.length === 4) {
    return authorsArray.join(";").concat("...");
  }
  return authors;
}

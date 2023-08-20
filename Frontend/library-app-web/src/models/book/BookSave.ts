import { Book } from "./Book";

export default interface BookSave {
  rowVersion: number;
  id: number;
  title: string;
  description: string;
  imageUrl: string;
  pagesNumber: number;
  publisherId: number;
  authorsIds: number[];
}

export function constructBookSave(book: Book): BookSave {
  var bookSave: BookSave = {
    rowVersion: book.rowVersion,
    id: book.id,
    title: book.title,
    description: book.description,
    imageUrl: book.imageUrl,
    pagesNumber: book.pagesNumber,
  } as BookSave;
  if (book.publisher !== undefined && book.publisher !== null) {
    bookSave.publisherId = book.publisher.id;
  }
  if (
    book.authors !== undefined &&
    book.authors !== null &&
    book.authors.length !== 0
  ) {
    bookSave.authorsIds = book.authors.map((author) => author.id);
  }
  return bookSave;
}

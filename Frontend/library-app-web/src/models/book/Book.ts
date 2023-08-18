import AuthorShort from "../author/AuthorShort";
import PublisherShort from "../publisher/PublisherShort";

export interface Book {
  rowVersion: number;
  id: number;
  title: string;
  description: string;
  imageUrl: string;
  pagesNumber: number;
  publisher: PublisherShort;
  authors: AuthorShort[];
}

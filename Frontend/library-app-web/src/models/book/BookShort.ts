export interface BookShortFromServer {
  id: number;
  title: string;
  publisher: string;
  authors: string;
}

export interface BookShort {
  id: number;
  title: string;
  publisher: { name: string };
  authors: string;
}

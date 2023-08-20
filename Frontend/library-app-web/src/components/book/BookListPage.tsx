import { Button, Card, IconButton, Tooltip } from "@material-tailwind/react";
import BackgroundImage from "../home/BackgroundImage";
import BookTable from "./BookTable";
import { BookShort } from "../../models/book/BookShort";
import { questionAlertIsSureAsync } from "../../services/alert/questionAlert";
import { deleteBookAsync } from "../../request/book/bookRequests";
import { successAlert } from "../../services/alert/successHandler";
import { useAppDispatch } from "../../store/config/hooks";
import { getBooksAsyncThunk } from "../../store/book/table/booksThunks";
import { handleBookDeleteError } from "../../services/alert/errorHandler";
import { Row } from "react-table";
import {
  BookOpenIcon,
  ClipboardDocumentIcon,
  PencilIcon,
  TrashIcon,
} from "@heroicons/react/24/outline";
import { useNavigate } from "react-router-dom";
import {
  ADD_BOOK_PAGE,
  get_LIST_COPIES_OF_BOOK_PAGE,
  get_UPDATE_BOOK_PAGE,
} from "../routes/AppRouter";

export interface BookViewCopiesNavigationState {
  book: BookShort;
}

export default function BookListPage() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  async function handleClickOnDeleteBookAsync(book: BookShort) {
    const confirmed = await questionAlertIsSureAsync(
      "Are you sure you want to delete?",
      `Book: ${book.title}${!book.authors ? "" : ` - ${book.authors}`}${
        !book.publisher || !book.publisher.name
          ? ""
          : `, published by ${book.publisher.name}`
      }`
    );
    if (confirmed === false) {
      return;
    }
    try {
      await deleteBookAsync(book.id);
      successAlert();
      await dispatch(getBooksAsyncThunk());
    } catch (error) {
      handleBookDeleteError();
    }
  }

  function rowActions(row: Row<{}>): JSX.Element {
    const book = row.original as BookShort;
    return (
      <div>
        <Tooltip content="View copies">
          <IconButton
            size="sm"
            variant="text"
            onClick={() => {
              navigate(get_LIST_COPIES_OF_BOOK_PAGE(book.id));
            }}
          >
            <BookOpenIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
        <Tooltip content="Edit">
          <IconButton
            size="sm"
            variant="text"
            onClick={() => navigate(get_UPDATE_BOOK_PAGE(book.id))}
          >
            <PencilIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
        <Tooltip content="Delete">
          <IconButton
            size="sm"
            variant="text"
            onClick={() => handleClickOnDeleteBookAsync(book)}
          >
            <TrashIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
      </div>
    );
  }

  function renderHeaderChildren(searchInputField: JSX.Element): JSX.Element {
    return (
      <div className="mt-2 flex justify-between">
        {searchInputField}
        <Button
          className="mx-2 px-4 border border-blue-gray-100 hover:border-blue-gray-300"
          color="white"
          onClick={() => navigate(ADD_BOOK_PAGE)}
        >
          <div className="flex justify-between items-center gap-3">
            <ClipboardDocumentIcon className="w-4 h-4" />
            <span className="text-xs text-gray-800">Add a new book</span>
          </div>
        </Button>
      </div>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        <div className="w-11/12 min-w-min h-4/5 my-4">
          <Card className="w-full h-full">
            <div className="flex justify-center items-center mt-2 font-bold text-lg">
              <h3>List of books</h3>
            </div>
            <BookTable
              rowActions={rowActions}
              renderHeaderChildren={renderHeaderChildren}
            />
          </Card>
        </div>
      </div>
    </BackgroundImage>
  );
}

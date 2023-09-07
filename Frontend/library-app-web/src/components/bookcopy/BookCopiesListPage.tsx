import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import BackgroundImage from "../home/BackgroundImage";
import { Button, Card, IconButton, Tooltip } from "@material-tailwind/react";
import BookCopiesTable from "./BookCopiesTable";
import {
  getBookAsyncThunk,
  getBookCopiesInAllBuildingsAsyncThunk,
} from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesThunks";
import BookCopiesState from "../../store/bookcopy/table/by-book-all-buildings/BookCopiesState";
import { HOME_PAGE } from "../routes/AppRouter";
import BookCopyStatusFilterDropdown from "./BookCopyStatusFilterDropdown";
import { bookCopiesActions } from "../../store/bookcopy/table/by-book-all-buildings/bookCopiesSlice";
import { Row } from "react-table";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import {
  ClipboardDocumentIcon,
  PencilIcon,
  TrashIcon,
} from "@heroicons/react/24/outline";
import { questionAlertIsSureAsync } from "../../services/alert/questionAlert";
import { discardBookCopyAsync } from "../../request/bookcopy/bookCopyRequests";
import { successAlert } from "../../services/alert/successHandler";
import { handleDiscardBookCopyError } from "../../services/alert/errorHandler";
import { constructAlertError } from "../../models/error/AlertError";
import ResponseError from "../../request/ResponseError";
import { Box, Modal } from "@mui/material";
import BookCopyUpdateContainer from "./BookCopyUpdateContainer";
import BookCopyAddContainer from "./BookCopyAddContainer";

export function BookCopiesListPage() {
  const { id } = useParams();
  const bookId = Number(id);
  const bookCopiesState: BookCopiesState = useAppSelector(
    (state) => state.bookCopies
  );
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [showBookCopyAddModal, setShowBookCopyAddModal] =
    useState<boolean>(false);
  const [showBookCopyUpdateModal, setShowBookCopyUpdateModal] =
    useState<boolean>(false);
  const [bookCopyUpdate, setBookCopyUpdate] = useState<BookCopyDisplay>(
    {} as BookCopyDisplay
  );

  async function loadBookAsync() {
    try {
      await dispatch(getBookAsyncThunk(bookId)).unwrap();
    } catch (error) {
      navigate(HOME_PAGE);
    }
  }

  useEffect(() => {
    loadBookAsync();
  }, []);

  async function handleClickOnDiscardBookCopyAsync(bookCopy: BookCopyDisplay) {
    const confirmed = await questionAlertIsSureAsync(
      "Are you sure you want to discard?",
      `Book copy: ${bookCopy.book.title}, ISBN: ${bookCopy.isbn}`
    );
    if (confirmed === false) {
      return;
    }
    try {
      await discardBookCopyAsync(bookCopy);
      successAlert();
      await dispatch(getBookCopiesInAllBuildingsAsyncThunk(bookId));
    } catch (error) {
      handleDiscardBookCopyError(constructAlertError(error as ResponseError));
    }
  }

  async function handleClickOnUpdateBookCopyAsync(bookCopy: BookCopyDisplay) {
    setBookCopyUpdate(bookCopy);
    setShowBookCopyUpdateModal(true);
  }

  async function closeBookCopyAddModalAsync() {
    setShowBookCopyAddModal(false);
    await dispatch(getBookCopiesInAllBuildingsAsyncThunk(bookId));
  }

  async function closeBookCopyUpdateModalAsync() {
    setShowBookCopyUpdateModal(false);
    setBookCopyUpdate({} as BookCopyDisplay);
    await dispatch(getBookCopiesInAllBuildingsAsyncThunk(bookId));
  }

  function rowActions(row: Row<{}>): JSX.Element {
    const bookCopy = row.original as BookCopyDisplay;
    return (
      <div>
        <Tooltip content="Edit">
          <IconButton
            size="sm"
            variant="text"
            onClick={() => handleClickOnUpdateBookCopyAsync(bookCopy)}
          >
            <PencilIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
        <Tooltip content="Discard">
          <IconButton
            size="sm"
            variant="text"
            onClick={() => handleClickOnDiscardBookCopyAsync(bookCopy)}
          >
            <TrashIcon color="gray" className="h-4 w-4" />
          </IconButton>
        </Tooltip>
      </div>
    );
  }

  function renderHeaderChildren(searchInputField: JSX.Element): JSX.Element {
    return (
      <div
        className="mt-2"
        style={{
          display: "grid",
          gridAutoFlow: "column",
          gridTemplateColumns: "auto 30%",
        }}
      >
        <div className="flex justify-start items-center gap-2 w-full">
          {searchInputField}
          <BookCopyStatusFilterDropdown
            status={bookCopiesState.status}
            onChange={(bookCopyStatus) =>
              dispatch(bookCopiesActions.setBookCopyStatus(bookCopyStatus))
            }
          />
        </div>
        <div className="flex justify-end items-center w-full">
          <Button
            className="mx-2 px-4 border border-blue-gray-100 hover:border-blue-gray-300"
            color="white"
            onClick={() => setShowBookCopyAddModal(true)}
          >
            <div className="flex justify-between items-center gap-3">
              <ClipboardDocumentIcon className="w-4 h-4" />
              <span className="text-xs text-gray-800">Add new copy</span>
            </div>
          </Button>
        </div>
      </div>
    );
  }

  function ModalAddBookCopy(): JSX.Element {
    return (
      <Modal
        open={showBookCopyAddModal}
        onClose={closeBookCopyAddModalAsync}
        className="flex justify-center items-center"
      >
        <Box
          sx={{
            width: "50%",
          }}
        >
          <BookCopyAddContainer
            book={bookCopiesState.book}
            close={closeBookCopyAddModalAsync}
          />
        </Box>
      </Modal>
    );
  }

  function ModalUpdateBookCopy(): JSX.Element {
    return (
      <Modal
        open={showBookCopyUpdateModal}
        onClose={closeBookCopyUpdateModalAsync}
        className="flex justify-center items-center"
      >
        <Box
          sx={{
            width: "50%",
          }}
        >
          <BookCopyUpdateContainer
            book={bookCopiesState.book}
            bookCopy={bookCopyUpdate}
            close={closeBookCopyUpdateModalAsync}
          />
        </Box>
      </Modal>
    );
  }

  return (
    <BackgroundImage>
      <div className="flex items-center justify-center h-full">
        <div className="w-7/12 my-4 min-w-min h-5/6">
          <ModalAddBookCopy />
          <ModalUpdateBookCopy />
          <Card className="h-full w-full">
            {bookCopiesState.book === undefined ||
            bookCopiesState.bookLoading ? (
              <></>
            ) : (
              <>
                <div className="flex justify-center items-center font-bold text-lg m-4">
                  {bookCopiesState.book.title}
                </div>
                {/* <BookInfo
                book={bookCopiesState.book}
                availableCopiesInBuildingCount={
                  bookCopiesState.availableCopiesInBuildingCount
                }
              /> */}
                <BookCopiesTable
                  renderHeaderChildren={renderHeaderChildren}
                  rowActions={rowActions}
                />
              </>
            )}
          </Card>
        </div>
      </div>
    </BackgroundImage>
  );
}

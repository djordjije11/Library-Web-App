import {
  Button,
  Card,
  CardBody,
  CardFooter,
  IconButton,
  Tooltip,
} from "@material-tailwind/react";
import FormField from "../shared/form/FormField";
import { FormEvent, useState } from "react";
import { Box, Modal } from "@mui/material";
import LendingsAddState from "../../store/lending/add/LendingsAddState";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MemberTable from "../member/MemberTable";
import MemberShort from "../../models/member/MemberShort";
import { lendingsAddActions } from "../../store/lending/add/lendingsAddSlice";
import { PencilSquareIcon } from "@heroicons/react/24/outline";
import { allValid } from "../../models/validation/ValidationResult";
import LendingsAddInputResults from "../../models/lending/LendingsAddInputResults";
import {
  validateRequiredAny,
  validateRequiredArray,
} from "../../validation/modelValidations";
import BookCopyTable from "../bookcopy/BookCopyTable";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import BookCopyCard from "../bookcopy/BookCopyCard";
import { addLendingsAsyncThunk } from "../../store/lending/add/lendingsAddThunks";
import { successAlert } from "../../services/alert/successHandler";
import { handleRecordNotFoundError } from "../../services/alert/errorHandler";

export default function LendingAddForm() {
  const lendingsAddState: LendingsAddState = useAppSelector(
    (state) => state.lendingsAdd
  );
  const dispatch = useAppDispatch();
  const [lendingsAddInputResults, setLendingsAddInputResults] =
    useState<LendingsAddInputResults>({} as LendingsAddInputResults);
  const [showMemberModal, setShowMemberModal] = useState<boolean>(false);
  const [showBookCopiesModal, setShowBookCopiesModal] =
    useState<boolean>(false);

  function handleSelectedMember(member: MemberShort) {
    dispatch(lendingsAddActions.setMember(member));
    setShowMemberModal(false);
  }

  function handleSelectedBookCopy(bookCopy: BookCopyDisplay) {
    dispatch(lendingsAddActions.addBookCopy(bookCopy));
    setShowBookCopiesModal(false);
  }

  function handleRemoveBookCopy(bookCopy: BookCopyDisplay) {
    dispatch(lendingsAddActions.removeBookCopy(bookCopy.id));
  }

  function FieldSelectMember(): JSX.Element {
    if (lendingsAddState.lendingsAdd.member === undefined) {
      return (
        <Button onClick={() => setShowMemberModal(true)} color="blue-gray">
          Select a member
        </Button>
      );
    }

    return (
      <div
        style={{
          display: "grid",
          gridAutoFlow: "column",
          gridTemplateColumns: "90% auto",
          gap: "10px",
        }}
      >
        <div className="w-full p-3 font-medium border rounded-md border-slate-300 bg-white">
          <span>
            {lendingsAddState.lendingsAdd.member.firstname}{" "}
            {lendingsAddState.lendingsAdd.member.lastname}
          </span>
        </div>
        <div className="flex items-center">
          <Tooltip content="Select a member">
            <IconButton
              color="white"
              style={{
                border: "solid 1px gray",
                height: "100%",
              }}
              onClick={() => setShowMemberModal(true)}
            >
              <PencilSquareIcon color="blue-gray" width={"20px"} />
            </IconButton>
          </Tooltip>
        </div>
      </div>
    );
  }

  function FieldSelectBookCopies(): JSX.Element {
    if (lendingsAddState.lendingsAdd.bookCopies.length === 0) {
      return (
        <Button onClick={() => setShowBookCopiesModal(true)} color="blue-gray">
          Select a book
        </Button>
      );
    }

    return (
      <>
        <Button onClick={() => setShowBookCopiesModal(true)} color="blue-gray">
          Select a book
        </Button>
        <div className="w-full max-h-52 flex flex-wrap gap-2 overflow-y-auto p-3 border rounded-md border-slate-300 bg-white">
          {lendingsAddState.lendingsAdd.bookCopies.map((bookCopy) => (
            <BookCopyCard
              key={bookCopy.id}
              bookCopy={bookCopy}
              onClose={() => handleRemoveBookCopy(bookCopy)}
            />
          ))}
        </div>
      </>
    );
  }

  function ModalSelectMember(): JSX.Element {
    return (
      <Modal
        open={showMemberModal}
        onClose={() => setShowMemberModal(false)}
        className="flex justify-center items-center"
      >
        <Box
          sx={{
            width: "50%",
            border: "none",
          }}
        >
          <Card>
            <MemberTable
              onSelectedRow={(event, row) =>
                handleSelectedMember(row.original as MemberShort)
              }
            />
          </Card>
        </Box>
      </Modal>
    );
  }

  function ModalSelectBookCopy(): JSX.Element {
    return (
      <Modal
        open={showBookCopiesModal}
        onClose={() => setShowBookCopiesModal(false)}
        className="flex justify-center items-center"
      >
        <Box sx={{ width: "80%", border: "none" }}>
          <Card>
            <BookCopyTable
              onSelectedRow={(event, row) =>
                handleSelectedBookCopy(row.original as BookCopyDisplay)
              }
            />
          </Card>
        </Box>
      </Modal>
    );
  }

  function validateForm(): boolean {
    const memberIdResult = validateRequiredAny(
      "Member",
      lendingsAddState.lendingsAdd.member
    );
    const bookCopiesResult = validateRequiredArray(
      "Books",
      lendingsAddState.lendingsAdd.bookCopies
    );
    setLendingsAddInputResults((prev) => ({
      ...prev,
      memberIdResult,
      bookCopiesResult,
    }));
    return allValid(memberIdResult, bookCopiesResult);
  }

  async function onSubmitAsync(event: FormEvent) {
    event.preventDefault();
    const formValid = validateForm();
    if (formValid === false) {
      return;
    }
    try {
      await dispatch(addLendingsAsyncThunk());
      successAlert();
    } catch (error) {
      handleRecordNotFoundError();
    }
  }

  return (
    <>
      <ModalSelectMember />
      <ModalSelectBookCopy />
      <Card className="my-4 w-full bg-blue-gray-50">
        <form onSubmit={onSubmitAsync}>
          <CardBody>
            <div className="flex justify-center font-bold">
              <h3>Note book lendings</h3>
            </div>
            <FormField
              name="member"
              label="Member"
              result={lendingsAddInputResults.memberIdResult}
            >
              <FieldSelectMember />
            </FormField>
            <FormField
              name="booksCopies"
              label="Books"
              result={lendingsAddInputResults.bookCopiesResult}
              wrapperClasses="mt-2"
            >
              <FieldSelectBookCopies />
            </FormField>
          </CardBody>
          <CardFooter>
            <Button type="submit" color="blue-gray">
              Submit
            </Button>
          </CardFooter>
        </form>
      </Card>
    </>
  );
}

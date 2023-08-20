import { Button, Card, CardBody, CardFooter } from "@material-tailwind/react";
import FormField from "../shared/form/FormField";
import { FormEvent, useState } from "react";
import LendingsAddState from "../../store/lending/add/LendingsAddState";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import MemberShort from "../../models/member/MemberShort";
import { lendingsAddActions } from "../../store/lending/add/lendingsAddSlice";
import { allValid } from "../../models/validation/ValidationResult";
import LendingsAddInputResults from "../../models/lending/LendingsAddInputResults";
import {
  validateRequiredAny,
  validateRequiredArray,
} from "../../validation/modelValidations";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import { addLendingsAsyncThunk } from "../../store/lending/add/lendingsAddThunks";
import { successAlert } from "../../services/alert/successHandler";
import { handleRecordNotFoundError } from "../../services/alert/errorHandler";
import ModalSelectMember from "../member/ModalSelectMember";
import ModalSelectBookCopy from "../bookcopy/ModalSelectBookCopy";
import SelectBookCopiesField from "../bookcopy/SelectBookCopiesField";
import MemberSelectField from "../member/MemberSelectField";

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
      <ModalSelectMember
        show={showMemberModal}
        close={() => setShowMemberModal(false)}
        handleSelectedMember={handleSelectedMember}
      />
      <ModalSelectBookCopy
        show={showBookCopiesModal}
        close={() => setShowBookCopiesModal(false)}
        handleSelectedBookCopy={handleSelectedBookCopy}
      />
      <Card className="my-4 w-full bg-blue-gray-50">
        <form onSubmit={onSubmitAsync}>
          <CardBody>
            <div className="flex justify-center font-bold">
              <h3>Note book lendings</h3>
            </div>
            <FormField
              name="member"
              label="Member"
              result={lendingsAddInputResults.memberResult}
            >
              <MemberSelectField
                member={lendingsAddState.lendingsAdd.member}
                onSelectClick={() => setShowMemberModal(true)}
              />
            </FormField>
            <FormField
              name="booksCopies"
              label="Books"
              result={lendingsAddInputResults.bookCopiesResult}
              wrapperClasses="mt-2"
            >
              <SelectBookCopiesField
                bookCopies={lendingsAddState.lendingsAdd.bookCopies}
                handleRemoveBookCopy={handleRemoveBookCopy}
                onSelectBookCopyClick={() => setShowBookCopiesModal(true)}
              />
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

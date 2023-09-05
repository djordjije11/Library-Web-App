import { FormEvent, useState } from "react";
import MemberShort from "../../models/member/MemberShort";
import { useAppDispatch, useAppSelector } from "../../store/config/hooks";
import { lendingsReturnActions } from "../../store/lending/return/lendingsReturnSlice";
import ModalSelectMember from "../member/ModalSelectMember";
import { Button, Card, CardBody, CardFooter } from "@material-tailwind/react";
import FormField from "../shared/form/FormField";
import {
  validateRequiredAny,
  validateRequiredArray,
} from "../../validation/modelValidations";
import { LendingsReturnInputResults } from "../../models/lending/LendingsReturnInputResults";
import { LendingsReturnState } from "../../store/lending/return/LendingsReturnState";
import {
  allValid,
  getResultError,
} from "../../models/validation/ValidationResult";
import { returnLendingsAsyncThunk } from "../../store/lending/return/lendingsReturnThunks";
import { successAlert } from "../../services/alert/successHandler";
import { handleRecordNotFoundError } from "../../services/alert/errorHandler";
import SelectBookCopiesField from "../bookcopy/SelectBookCopiesField";
import {
  LendingIncludingBookCopy,
  getBookCopiesFromLendings,
} from "../../models/lending/LendingIncludingBookCopy";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";
import ModalSelectLendingReturn from "./ModalSelectLendingReturn";
import { lendingsByMemberUnreturnedActions } from "../../store/lending/table/by-member/unreturned/lendingsByMemberUnreturnedSlice";
import MemberSelectField from "../member/MemberSelectField";

export default function LendingReturnForm() {
  const lendingsReturnState: LendingsReturnState = useAppSelector(
    (state) => state.lendingsReturn
  );
  const dispatch = useAppDispatch();
  const [showMemberModal, setShowMemberModal] = useState<boolean>(false);
  const [showLendingsModal, setShowLendingsModal] = useState<boolean>(false);
  const [lendingsReturnInputResults, setLendingsReturnInputResults] =
    useState<LendingsReturnInputResults>({} as LendingsReturnInputResults);

  function handleSelectedMember(member: MemberShort) {
    dispatch(lendingsReturnActions.setMember(member));
    dispatch(lendingsByMemberUnreturnedActions.setMember(member));
    setShowMemberModal(false);
  }

  function handleSelectedLending(lending: LendingIncludingBookCopy) {
    dispatch(lendingsReturnActions.addLending(lending));
    setShowLendingsModal(false);
  }

  function handleRemoveLending(bookCopy: BookCopyDisplay) {
    dispatch(lendingsReturnActions.removeLendingByBookCopyId(bookCopy.id));
  }

  function handleSelectBookCopyClick() {
    if (lendingsReturnState.lendingsByMember.member === undefined) {
      setLendingsReturnInputResults((prev) => ({
        ...prev,
        lendingsResult: getResultError("Select a member first."),
      }));
      return;
    }
    setShowLendingsModal(true);
  }

  function validateForm(): boolean {
    const memberIdResult = validateRequiredAny(
      "Member",
      lendingsReturnState.lendingsByMember.member
    );
    const lendingsResult = validateRequiredArray(
      "Books",
      lendingsReturnState.lendingsByMember.lendings
    );
    setLendingsReturnInputResults((prev) => ({
      ...prev,
      memberIdResult,
      lendingsResult,
    }));
    return allValid(memberIdResult, lendingsResult);
  }

  async function onSubmitAsync(event: FormEvent) {
    event.preventDefault();
    const formValid = validateForm();
    if (formValid === false) {
      return;
    }
    try {
      await dispatch(returnLendingsAsyncThunk());
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
      <ModalSelectLendingReturn
        show={showLendingsModal}
        close={() => setShowLendingsModal(false)}
        handleSelectedLending={handleSelectedLending}
      />
      <Card className="my-4 w-full bg-blue-gray-50">
        <form onSubmit={onSubmitAsync}>
          <CardBody>
            <div className="flex justify-center font-bold">
              <h3>Note book returnments</h3>
            </div>
            <FormField
              name="member"
              label="Member"
              result={lendingsReturnInputResults.memberResult}
            >
              <MemberSelectField
                member={lendingsReturnState.lendingsByMember.member}
                onSelectClick={() => setShowMemberModal(true)}
              />
            </FormField>
            <FormField
              name="lendings"
              label="Books"
              result={lendingsReturnInputResults.lendingsResult}
              wrapperClasses="mt-2"
            >
              <SelectBookCopiesField
                bookCopies={getBookCopiesFromLendings(
                  lendingsReturnState.lendingsByMember.lendings
                )}
                handleRemoveBookCopy={handleRemoveLending}
                onSelectBookCopyClick={handleSelectBookCopyClick}
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

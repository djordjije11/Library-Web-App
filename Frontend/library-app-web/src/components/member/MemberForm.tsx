import { useState, FormEvent } from "react";
import { Button, Card, CardBody, CardFooter } from "@material-tailwind/react";
import FormInput from "../shared/form/FormInput";
import { allValid } from "../../models/validation/ValidationResult";
import {
  validateBirthday,
  validateEmail,
  validateFirstname,
  validateIdCardNumber,
  validateLastname,
} from "../../validation/modelValidations";
import GenderRadioGroup from "../shared/form/GenderRadioGroup";
import FormField from "../shared/form/FormField";
import { Gender } from "../../models/enums/Gender";
import FormDate from "../shared/form/FormDate";
import AlertError from "../../models/error/AlertError";
import MemberDetail from "../../models/member/MemberDetail";
import MemberInputResults from "../../models/member/MemberInputResults";
import { successAlert } from "../../services/alert/successHandler";
import { handleMemberFormError } from "../../services/alert/errorHandler";
import {
  convertDateToJsonDate,
  convertJsonDateToDate,
} from "../../models/parsers/dateParser";

export interface MemberFormProps {
  member?: MemberDetail;
  onSubmitAsync: (member: MemberDetail) => Promise<MemberDetail>;
  clearOnSubmit: boolean;
  formHeader: JSX.Element;
}

export default function MemberForm(props: MemberFormProps) {
  const { onSubmitAsync, clearOnSubmit, formHeader } = props;
  const [member, setMember] = useState<MemberDetail>(
    props.member || ({} as MemberDetail)
  );
  const [memberInputResults, setMemberInputResults] =
    useState<MemberInputResults>({} as MemberInputResults);

  const maxBirthdayDate = new Date();
  maxBirthdayDate.setDate(maxBirthdayDate.getDate() - 365 * 16);

  function validateForm(): boolean {
    const idCardNumberResult = validateIdCardNumber(member.idCardNumber);
    const firstnameResult = validateFirstname(member.firstname);
    const lastnameResult = validateLastname(member.lastname);
    const emailResult = validateEmail(member.email);
    const birthdayResult = validateBirthday(member.birthday);
    setMemberInputResults((prev) => {
      return {
        ...prev,
        idCardNumberResult,
        firstnameResult,
        lastnameResult,
        emailResult,
        birthdayResult,
      };
    });
    return allValid(
      idCardNumberResult,
      firstnameResult,
      lastnameResult,
      emailResult,
      birthdayResult
    );
  }

  function clearFormFields() {
    setMember({} as MemberDetail);
  }

  async function handleSubmitAsync(event: FormEvent) {
    event.preventDefault();
    const formValid = validateForm();
    if (formValid === false) {
      return;
    }
    try {
      await onSubmitAsync(member);
      if (clearOnSubmit) {
        clearFormFields();
      }
      successAlert();
    } catch (error) {
      handleMemberFormError(error as AlertError);
    }
  }

  function handleGenderChanged(gender: Gender) {
    setMember((prev) => {
      return { ...prev, gender };
    });
  }

  function handleBirthdayChanged(birthday: Date) {
    setMember((prev) => {
      return { ...prev, birthday: convertDateToJsonDate(birthday) };
    });
  }

  function handlePropertyChanged(event: React.ChangeEvent<HTMLInputElement>) {
    setMember((prev) => {
      return {
        ...prev,
        [event.target.name]: event.target.value,
      } as MemberDetail;
    });
  }

  return (
    <Card className="my-4 w-full bg-blue-gray-50">
      <form onSubmit={handleSubmitAsync} noValidate autoComplete="off">
        <CardBody>
          <div>{formHeader}</div>
          <FormField
            name="idCardNumber"
            label="ID Card Number"
            result={memberInputResults.idCardNumberResult}
          >
            <FormInput
              name="idCardNumber"
              type="text"
              value={member.idCardNumber}
              onChange={handlePropertyChanged}
            />
          </FormField>
          <FormField
            name="firstname"
            label="Firstname"
            result={memberInputResults.firstnameResult}
          >
            <FormInput
              name="firstname"
              type="text"
              value={member.firstname}
              onChange={handlePropertyChanged}
            />
          </FormField>
          <FormField
            name="lastname"
            label="Lastname"
            result={memberInputResults.lastnameResult}
          >
            <FormInput
              name="lastname"
              type="text"
              value={member.lastname}
              onChange={handlePropertyChanged}
            />
          </FormField>
          <FormField name="gender" label="Gender">
            <GenderRadioGroup
              value={member.gender}
              onChange={handleGenderChanged}
            />
          </FormField>
          <FormField
            name="email"
            label="Email"
            result={memberInputResults.emailResult}
          >
            <FormInput
              name="email"
              type="email"
              value={member.email}
              onChange={handlePropertyChanged}
            />
          </FormField>
          <FormField
            name="birthday"
            label="Birthday"
            result={memberInputResults.birthdayResult}
          >
            <FormDate
              title="Birthday"
              defaultDate={convertJsonDateToDate(member.birthday)}
              onChange={handleBirthdayChanged}
              maxDate={maxBirthdayDate}
            />
          </FormField>
        </CardBody>
        <CardFooter className="pt-0 flex justify-start items-center gap-2">
          <Button type="submit">Submit</Button>
        </CardFooter>
      </form>
    </Card>
  );
}

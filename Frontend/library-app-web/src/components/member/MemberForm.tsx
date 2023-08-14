import { useState, FormEvent, SetStateAction, Dispatch } from "react";
import { Button, Card, CardBody, CardFooter } from "@material-tailwind/react";
import Member from "../../models/member/Member";
import FormInput from "../form/FormInput";
import { MemberInputResults } from "../../models/member/Member";
import { allValid } from "../../models/validation/ValidationResult";
import {
  validateEmail,
  validateFirstname,
  validateIdCardNumber,
  validateLastname,
} from "../../validation/modelValidations";
import GenderRadioGroup from "../form/GenderRadioGroup";
import FormField from "../form/FormField";
import { Gender } from "../../models/enums/Gender";
import FormDate from "../form/FormDate";
import Swal from "sweetalert2";
import { toast } from "react-toastify";
import AlertError from "../../models/error/AlertError";

export interface MemberFormProps {
  member: Member;
  setMember: Dispatch<SetStateAction<Member>>;
  onSubmitAsync: (member: Member) => Promise<Member | AlertError>;
}

export default function MemberForm(props: MemberFormProps) {
  const { member, setMember, onSubmitAsync } = props;

  const [memberInputResults, setMemberInputResults] =
    useState<MemberInputResults>({} as MemberInputResults);

  const maxBirthdayDate = new Date();
  maxBirthdayDate.setDate(maxBirthdayDate.getDate() - 365 * 16);

  function validateForm(): boolean {
    const idCardNumberResult = validateIdCardNumber(member.idCardNumber);
    const firstnameResult = validateFirstname(member.firstname);
    const lastnameResult = validateLastname(member.lastname);
    const emailResult = validateEmail(member.email);
    setMemberInputResults((prev) => {
      return {
        ...prev,
        idCardNumberResult,
        firstnameResult,
        lastnameResult,
        emailResult,
      };
    });
    return allValid(
      idCardNumberResult,
      firstnameResult,
      lastnameResult,
      emailResult
    );
  }

  async function handleSubmitAsync(event: FormEvent) {
    event.preventDefault();
    const formValid = validateForm();
    if (formValid === false) {
      return;
    }
    try {
      await onSubmitAsync(member);
      toast.success("Successfully completed.");
    } catch (error) {
      Swal.fire({
        title: "Invalid request",
        text: "ID Card Number is already being used by another member in the system.",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  }

  function handleGenderChanged(gender: Gender) {
    setMember((prev) => {
      return { ...prev, gender };
    });
  }

  function handleBirthdayChanged(birthday: Date) {
    setMember((prev) => {
      return { ...prev, birthday: birthday.toISOString().split("T")[0] };
    });
  }

  function handlePropertyChanged(event: React.ChangeEvent<HTMLInputElement>) {
    setMember((prev) => {
      return {
        ...prev,
        [event.target.name]: event.target.value,
      } as Member;
    });
  }

  return (
    <div>
      <form onSubmit={handleSubmitAsync} noValidate autoComplete="off">
        <Card className="mt-10 w-full bg-blue-gray-50">
          <CardBody>
            <div className="flex justify-center font-bold">
              <h3>Add a new member</h3>
            </div>
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
            <FormField name="birthday" label="Birthday">
              <FormDate
                title="Birthday"
                onChange={handleBirthdayChanged}
                maxDate={maxBirthdayDate}
              />
            </FormField>
          </CardBody>
          <CardFooter className="pt-0 flex justify-start items-center gap-2">
            <Button type="submit">Submit</Button>
          </CardFooter>
        </Card>
      </form>
    </div>
  );
}

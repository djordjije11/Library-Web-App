import { useState, FormEvent } from "react";
import { Button, Card, CardBody, CardFooter } from "@material-tailwind/react";
import Member from "../../models/member/Member";
import FormInput from "../form/FormInput";
import MemberInputResults from "../../models/member/MemberInputResults";
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
import { addMemberAsyncThunk } from "../../store/member-add/memberAddThunks";
import { useAppDispatch } from "../../store/config/hooks";

export default function MemberAddForm() {
  const [memberInput, setMemberInput] = useState<Member>({} as Member);
  const [memberInputResults, setMemberInputResults] =
    useState<MemberInputResults>({} as MemberInputResults);
  const dispatch = useAppDispatch();

  function validateForm(): boolean {
    const idCardNumberResult = validateIdCardNumber(memberInput.idCardNumber);
    const firstnameResult = validateFirstname(memberInput.firstname);
    const lastnameResult = validateLastname(memberInput.lastname);
    const emailResult = validateEmail(memberInput.email);
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
    console.log(memberInput);
    await dispatch(addMemberAsyncThunk(memberInput));
  }

  function handleGenderChanged(gender: Gender) {
    setMemberInput((prev) => {
      return { ...prev, gender };
    });
  }

  function handlePropertyChanged(event: React.ChangeEvent<HTMLInputElement>) {
    setMemberInput((prev) => {
      return {
        ...prev,
        [event.target.name]: event.target.value,
      } as Member;
    });
  }

  return (
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
              value={memberInput.idCardNumber}
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
              value={memberInput.firstname}
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
              value={memberInput.lastname}
              onChange={handlePropertyChanged}
            />
          </FormField>
          <FormField name="gender" label="Gender">
            <GenderRadioGroup
              value={memberInput.gender}
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
              value={memberInput.email}
              onChange={handlePropertyChanged}
            />
          </FormField>
        </CardBody>
        <CardFooter className="pt-0">
          <Button type="submit">Submit</Button>
        </CardFooter>
      </Card>
    </form>
  );
}

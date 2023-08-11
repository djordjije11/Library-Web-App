import { Gender } from "../enums/Gender";
import FormInputResult from "../validation/ValidationResult";

export interface MemberInputResults {
  idCardNumberResult: FormInputResult;
  firstnameResult: FormInputResult;
  lastnameResult: FormInputResult;
  emailResult: FormInputResult;
}

export default interface Member {
  id: number;
  idCardNumber: string;
  firstname: string;
  lastname: string;
  gender: Gender;
  email: string;
  birthday: string;
}

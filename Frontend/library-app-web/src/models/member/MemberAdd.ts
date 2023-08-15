import { Gender } from "../enums/Gender";

export default interface MemberAdd {
  idCardNumber: string;
  firstname: string;
  lastname: string;
  gender?: Gender;
  email: string;
  birthday: string;
}

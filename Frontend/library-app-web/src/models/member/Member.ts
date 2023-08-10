import { Gender } from "../enums/Gender";

export default interface Member {
  id: number;
  idCardNumber: string;
  firstname: string;
  lastname: string;
  gender: Gender;
  email: string;
  birthday: string;
}

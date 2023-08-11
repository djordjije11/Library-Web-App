import ValidationResult from "../validation/ValidationResult";

export interface LoginInputResults {
  usernameResult: ValidationResult;
  passwordResult: ValidationResult;
}
export default interface LoginInput {
  username: string;
  password: string;
}

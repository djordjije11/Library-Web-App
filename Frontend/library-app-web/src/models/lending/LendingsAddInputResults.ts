import ValidationResult from "../validation/ValidationResult";

export default interface LendingsAddInputResults {
  memberResult: ValidationResult;
  bookCopiesResult: ValidationResult;
}

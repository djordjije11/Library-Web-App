import ValidationResult, {
  getResultError,
  getResultValid,
} from "../models/validation/ValidationResult";

export function checkNotBlank(value?: string): boolean {
  return value !== null && value !== undefined && value.length > 0;
}

export function checkNotEmpty(array: any[]): boolean {
  return array != undefined && array !== null && array.length !== 0;
}

export function checkBetweenInclusive(
  value: number,
  min: number,
  max: number
): boolean {
  return value >= min && value <= max;
}

export function validateIdCardNumber(idCardNumber: string): ValidationResult {
  if (checkNotBlank(idCardNumber) === false) {
    return getResultError("ID Card Number is required.");
  }
  if (checkBetweenInclusive(idCardNumber.length, 10, 20) === false) {
    return getResultError(
      "ID Card Number must be between 10 and 20 characters."
    );
  }
  const regex = /^\d{10,20}$/;
  if (regex.test(idCardNumber) === false) {
    return getResultError("ID Card Number is not valid.");
  }
  return getResultValid();
}

export function validateRequired(
  propertyName: string,
  propertyValue: string
): ValidationResult {
  return checkNotBlank(propertyValue)
    ? getResultValid()
    : getResultError(`${propertyName} is required.`);
}

export function validateRequiredAny(
  propertyName: string,
  propertyValue: any
): ValidationResult {
  return propertyValue !== undefined && propertyValue !== null
    ? getResultValid()
    : getResultError(`${propertyName} is required.`);
}

export function validateRequiredArray(
  propertyName: string,
  propertyValue: any[]
): ValidationResult {
  return checkNotEmpty(propertyValue)
    ? getResultValid()
    : getResultError(`${propertyName} are required.`);
}

export function validateFirstname(firstname: string): ValidationResult {
  return validateRequired("Firstname", firstname);
}

export function validateLastname(lastname: string): ValidationResult {
  return validateRequired("Lastname", lastname);
}

export function validateEmail(email: string): ValidationResult {
  if (checkNotBlank(email) === false) {
    return getResultError("Email is required.");
  }
  const regex = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
  if (regex.test(email.toLowerCase()) === false) {
    return getResultError("Email is not valid.");
  }
  return getResultValid();
}

export function validateBirthday(birthday: string): ValidationResult {
  return validateRequired("Birthday", birthday);
}

export function validateUsername(username: string): ValidationResult {
  if (checkNotBlank(username) === false) {
    return getResultError("Username is required.");
  }
  if (checkBetweenInclusive(username.length, 3, 30) === false) {
    return getResultError("Username must be between 3 and 30 characters.");
  }
  return getResultValid();
}

export function validatePassword(password: string): ValidationResult {
  if (checkNotBlank(password) === false) {
    return getResultError("Password is required.");
  }
  const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
  if (regex.test(password) === false) {
    return getResultError("Password is not valid.");
  }
  return getResultValid();
}

export function validateIsbn(isbn: string): ValidationResult {
  if (checkNotBlank(isbn) === false) {
    return getResultError("ISBN is required.");
  }
  const regex = /^\d{3}-\d{2}-\d{4}-\d{3}-\d{1}$/;
  if (regex.test(isbn) === false) {
    return getResultError("ISBN is not valid.");
  }
  return getResultValid();
}

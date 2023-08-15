import ValidationResult, {
  getResultError,
  getResultValid,
} from "../models/validation/ValidationResult";

export function checkNotBlank(value?: string): boolean {
  return value !== null && value !== undefined && value.length > 0;
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
    return getResultError("ID Card Number must be valid.");
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
    return getResultError("Email must be valid.");
  }
  return getResultValid();
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
    return getResultError("Password must be valid.");
  }
  return getResultValid();
}

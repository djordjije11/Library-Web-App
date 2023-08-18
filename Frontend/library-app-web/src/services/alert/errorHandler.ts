import Swal from "sweetalert2";
import AlertError from "../../models/error/AlertError";
import { ErrorType } from "../../models/error/ErrorType";

const UNKNOWN_ERROR_TEXT = "Something wrong happened. Try to reload the page.";
const RECORD_NOT_VALID_ERROR_TEXT = "The record is not valid.";
const RECORD_NOT_FOUND_ERROR_TEXT =
  "The record does not exist in the database. Try to reload the page.";
const RECORD_NOT_CURRENT_VERSION_ERROR_TEXT =
  "The record is not up-to-date. Try to reload the page.";

export function handleRecordNotFoundError() {
  Swal.fire({
    title: "Invalid request",
    text: "The record does not exist in the database. Try to reload the page.",
    icon: "error",
    confirmButtonText: "OK",
  });
}

export function handleLoginFormError() {
  Swal.fire({
    title: "Invalid request",
    text: "Invalid username and password.",
    icon: "error",
    confirmButtonText: "OK",
  });
}

export function handleMemberDeleteError() {
  Swal.fire({
    title: "Invalid request",
    text: "A member with a history of lendings cannot be deleted.",
    icon: "error",
    confirmButtonText: "OK",
  });
}

export function handleMemberFormError(error: AlertError) {
  var title = "Invalid request";
  var text = "";
  switch (error.error) {
    case ErrorType.RECORD_NOT_FOUND:
      text = RECORD_NOT_FOUND_ERROR_TEXT;
      break;
    case ErrorType.RECORD_NOT_CURRENT_VERSION:
      text = RECORD_NOT_CURRENT_VERSION_ERROR_TEXT;
      break;
    case ErrorType.REQUEST_NOT_VALID:
      text =
        "ID Card Number is already being used by another member in the system.";
      break;
    case ErrorType.RECORD_NOT_VALID:
      text = error.message || RECORD_NOT_VALID_ERROR_TEXT;
      break;
    default:
      text = UNKNOWN_ERROR_TEXT;
      break;
  }

  Swal.fire({
    title,
    text,
    icon: "error",
    confirmButtonText: "OK",
  });
}

export function handleBookDeleteError() {
  Swal.fire({
    title: "Invalid request",
    text: "There are copies of the book in the database and the book cannot be deleted.",
    icon: "error",
    confirmButtonText: "OK",
  });
}

export function handleDiscardBookCopyError(error: AlertError) {
  var title = "Invalid request";
  var text = "";
  switch (error.error) {
    case ErrorType.RECORD_NOT_FOUND:
      text = RECORD_NOT_FOUND_ERROR_TEXT;
      break;
    case ErrorType.RECORD_NOT_CURRENT_VERSION:
      text = RECORD_NOT_CURRENT_VERSION_ERROR_TEXT;
      break;
    case ErrorType.REQUEST_NOT_AUTHORIZED:
      text = "The book copy is not in the building, so you cannot discard it.";
      break;
    default:
      text = UNKNOWN_ERROR_TEXT;
      break;
  }

  Swal.fire({
    title,
    text,
    icon: "error",
    confirmButtonText: "OK",
  });
}

export function handleBookCopyFormError(error: AlertError) {
  var title = "Invalid request";
  var text = "";
  switch (error.error) {
    case ErrorType.RECORD_NOT_FOUND:
      text = RECORD_NOT_FOUND_ERROR_TEXT;
      break;
    case ErrorType.RECORD_NOT_CURRENT_VERSION:
      text = RECORD_NOT_CURRENT_VERSION_ERROR_TEXT;
      break;
    case ErrorType.REQUEST_NOT_AUTHORIZED:
      text =
        "The book copy is not in the building, so you are not allowed to edit it.";
      break;
    case ErrorType.REQUEST_NOT_VALID:
      text = "ISBN must be unique.";
      break;
    default:
      text = UNKNOWN_ERROR_TEXT;
      break;
  }

  Swal.fire({
    title,
    text,
    icon: "error",
    confirmButtonText: "OK",
  });
}

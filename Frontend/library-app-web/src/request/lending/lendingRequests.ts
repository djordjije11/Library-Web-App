import axios from "axios";
import {
  LendingsAdd,
  constructLendingsAddFromServer,
} from "../../models/lending/LendingsAdd";
import { ADD_LENDINGS_URL } from "../apiUrls";
import { getHeaders } from "../requestHeaders";
import { BookCopyDisplay } from "../../models/bookcopy/BookCopyDisplay";

export async function addLendingsAsync(
  lendingsAdd: LendingsAdd
): Promise<void> {
  const response = await axios.post(
    ADD_LENDINGS_URL,
    constructLendingsAddFromServer(lendingsAdd),
    {
      headers: getHeaders(),
    }
  );
}

import axios from "axios";
import LendingsAdd from "../../models/lending/LendingsAdd";
import { ADD_LENDINGS_URL } from "../apiUrls";
import { getHeaders } from "../requestHeaders";

export async function addLendingsAsync(
  lendingsAdd: LendingsAdd
): Promise<void> {
  const response = await axios.post(ADD_LENDINGS_URL, lendingsAdd, {
    headers: getHeaders(),
  });
}

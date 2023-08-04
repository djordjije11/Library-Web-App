import axios from "axios";
import LoginInput from "../../models/authentication/LoginInput";
import { LOGIN_URL } from "../apiUrls";

export async function loginAsync(loginInput : LoginInput) : Promise<string> {
    const response = await axios.post(LOGIN_URL, loginInput);
    return response.data.token;
}
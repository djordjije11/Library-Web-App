import AlertError from "../../models/error/AlertError";

export default interface AlertState {
    show: boolean,
    error?: AlertError
}
import AlertError from "../../models/alert/AlertError";

export default interface AlertState {
    show: boolean,
    error?: AlertError
}
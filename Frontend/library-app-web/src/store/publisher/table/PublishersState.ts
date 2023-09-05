import AlertError from "../../../models/error/AlertError";
import PublisherShort from "../../../models/publisher/PublisherShort";
import RequestQueryParams from "../../../models/request/RequestQueryParams";

export default interface PublishersState {
  publishers: PublisherShort[];
  totalPages: number;
  totalItemsCount: number;
  requestQueryParams: RequestQueryParams;
  loading: boolean;
  isError: boolean;
  error?: AlertError;
}

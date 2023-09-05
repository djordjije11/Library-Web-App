import {
  AnyAction,
  ThunkAction,
  ThunkDispatch,
  configureStore,
} from "@reduxjs/toolkit";
import { ToolkitStore } from "@reduxjs/toolkit/dist/configureStore";
import authSlice from "../authentication/authSlice";
import membersSlice from "../member/table/membersSlice";
import memberAddSlice from "../member/add/memberAddSlice";
import memberUpdateSlice from "../member/update/memberUpdateSlice";
import lendingsAddSlice from "../lending/add/lendingsAddSlice";
import AuthState from "../authentication/AuthState";
import MembersState from "../member/table/MembersState";
import MemberAddState from "../member/add/MemberAddState";
import MemberUpdateState from "../member/update/MemberUpdateState";
import LendingsAddState from "../lending/add/LendingsAddState";
import BooksCopiesState from "../bookcopy/table/by-all-books-in-building/BooksCopiesState";
import booksCopiesSlice from "../bookcopy/table/by-all-books-in-building/booksCopiesSlice";
import { LendingsReturnState } from "../lending/return/LendingsReturnState";
import lendingsReturnSlice from "../lending/return/lendingsReturnSlice";
import { LendingsByMemberUnreturnedState } from "../lending/table/by-member/unreturned/LendingsByMemberUnreturnedState";
import lendingsByMemberUnreturnedSlice from "../lending/table/by-member/unreturned/lendingsByMemberUnreturnedSlice";
import BooksState from "../book/table/BooksState";
import booksSlice from "../book/table/booksSlice";
import BookCopiesState from "../bookcopy/table/by-book-all-buildings/BookCopiesState";
import bookCopiesSlice from "../bookcopy/table/by-book-all-buildings/bookCopiesSlice";
import PublishersState from "../publisher/table/PublishersState";
import publishersSlice from "../publisher/table/publishersSlice";
import AuthorsState from "../author/table/AuthorsState";
import authorsSlice from "../author/table/authorsSlice";
import { LendingsByMemberState } from "../lending/table/by-member/all/LendingsByMemberState";
import lendingsByMemberSlice from "../lending/table/by-member/all/lendingsByMemberSlice";

export interface StoreState {
  auth: AuthState;
  members: MembersState;
  memberAdd: MemberAddState;
  memberUpdate: MemberUpdateState;
  books: BooksState;
  booksCopies: BooksCopiesState;
  bookCopies: BookCopiesState;
  publishers: PublishersState;
  authors: AuthorsState;
  lendingsAdd: LendingsAddState;
  lendingsReturn: LendingsReturnState;
  lendingsByMemberUnreturned: LendingsByMemberUnreturnedState;
  lendingsByMember: LendingsByMemberState;
}

const store: ToolkitStore = configureStore({
  reducer: {
    auth: authSlice,
    members: membersSlice,
    memberAdd: memberAddSlice,
    memberUpdate: memberUpdateSlice,
    books: booksSlice,
    booksCopies: booksCopiesSlice,
    bookCopies: bookCopiesSlice,
    publishers: publishersSlice,
    authors: authorsSlice,
    lendingsAdd: lendingsAddSlice,
    lendingsReturn: lendingsReturnSlice,
    lendingsByMemberUnreturned: lendingsByMemberUnreturnedSlice,
    lendingsByMember: lendingsByMemberSlice,
  },
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export type TypedDispatch<T> = ThunkDispatch<T, any, AnyAction>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  AnyAction
>;

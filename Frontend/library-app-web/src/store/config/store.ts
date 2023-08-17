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
import BooksCopiesState from "../bookcopy/table/BooksCopiesState";
import booksCopiesSlice from "../bookcopy/table/booksCopiesSlice";
import { LendingsReturnState } from "../lending/return/LendingsReturnState";
import lendingsReturnSlice from "../lending/return/lendingsReturnSlice";
import { LendingsUnreturnedState } from "../lending/table/unreturned/LendingsUnreturnedState";
import lendingsUnreturnedSlice from "../lending/table/unreturned/lendingsUnreturnedSlice";

export interface StoreState {
  auth: AuthState;
  members: MembersState;
  memberAdd: MemberAddState;
  memberUpdate: MemberUpdateState;
  booksCopies: BooksCopiesState;
  lendingsAdd: LendingsAddState;
  lendingsReturn: LendingsReturnState;
  lendingsUnreturned: LendingsUnreturnedState;
}

const store: ToolkitStore = configureStore({
  reducer: {
    auth: authSlice,
    members: membersSlice,
    memberAdd: memberAddSlice,
    memberUpdate: memberUpdateSlice,
    booksCopies: booksCopiesSlice,
    lendingsAdd: lendingsAddSlice,
    lendingsReturn: lendingsReturnSlice,
    lendingsUnreturned: lendingsUnreturnedSlice,
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

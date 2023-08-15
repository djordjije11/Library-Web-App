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

const store: ToolkitStore = configureStore({
  reducer: {
    auth: authSlice,
    members: membersSlice,
    memberAdd: memberAddSlice,
    memberUpdate: memberUpdateSlice,
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

import {
  AnyAction,
  ThunkAction,
  ThunkDispatch,
  configureStore,
} from "@reduxjs/toolkit";
import { ToolkitStore } from "@reduxjs/toolkit/dist/configureStore";
import authSlice from "../authentication/authSlice";
import membersSlice from "../member/membersSlice";
import loaderSlice from "../loader/loaderSlice";
import memberAddSlice from "../member-add/memberAddSlice";

const store: ToolkitStore = configureStore({
  reducer: {
    auth: authSlice,
    members: membersSlice,
    loader: loaderSlice,
    memberAdd: memberAddSlice,
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

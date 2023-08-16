import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import LendingsAddState from "./LendingsAddState";
import { addLendingsAsyncThunk } from "./lendingsAddThunks";
import LendingsAdd from "../../../models/lending/LendingsAdd";

const initialState: LendingsAddState = {
  lendingsAdd: {} as LendingsAdd,
} as LendingsAddState;

const lendingsAddSlice = createSlice({
  name: "lendingsAdd",
  initialState,
  reducers: {
    setLendingsAdd: (state, action: PayloadAction<LendingsAdd>) => {
      state.lendingsAdd = action.payload;
    },
    setMemberId: (state, action: PayloadAction<number>) => {
      state.lendingsAdd.memberId = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(addLendingsAsyncThunk.pending, (state) => {
      state.loading = true;
    });
    builder.addCase(addLendingsAsyncThunk.rejected, (state, action) => {
      state.isError = true;
      state.error = action.payload;
      state.loading = false;
    });
    builder.addCase(addLendingsAsyncThunk.fulfilled, (state, action) => {
      state.lendingsAdd = {} as LendingsAdd;
      state.loading = false;
    });
  },
});

export default lendingsAddSlice.reducer;
export const lendingsAddActions = lendingsAddSlice.actions;

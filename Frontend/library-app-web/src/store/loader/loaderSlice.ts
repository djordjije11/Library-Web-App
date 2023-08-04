import { PayloadAction, createSlice } from "@reduxjs/toolkit";

const initialState = {
    loading: false
};

const loaderSlice = createSlice({
    name: "loader",
    initialState,
    reducers: {
        show: (state, action: PayloadAction<boolean>) => {
            state.loading = action.payload;
        }
    }
})

export default loaderSlice.reducer;
export const loaderActions = loaderSlice.actions;
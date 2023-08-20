import { createSlice } from "@reduxjs/toolkit";
import BookAddState from "./BookAddState";

const initialState = {} as BookAddState;

const bookAddSlice = createSlice({
  name: "bookAdd",
  initialState,
  reducers: {},
  extraReducers: (builder) => {},
});

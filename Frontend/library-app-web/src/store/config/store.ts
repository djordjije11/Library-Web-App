import { AnyAction, ThunkDispatch, configureStore } from '@reduxjs/toolkit'
import { ToolkitStore } from '@reduxjs/toolkit/dist/configureStore';
import authSlice from '../authentication/authSlice';
import membersSlice from '../member/membersSlice';
import alertSlice from '../alert/alertSlice';
import loaderSlice from '../loader/loaderSlice';

const store : ToolkitStore = configureStore({
    reducer:{
        auth: authSlice,
        members: membersSlice,
        alert: alertSlice,
        loader: loaderSlice
    }
});

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export type TypedDispatch<T> = ThunkDispatch<T, any, AnyAction>;
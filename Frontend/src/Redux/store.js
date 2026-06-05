import { configureStore } from '@reduxjs/toolkit';
import userReducer from "../Redux/userSlice";
import Eventlist from '../Components/Eventlist';

export const store = configureStore({
  reducer: {
    user: userReducer,
    eventlist : Eventlist,
  },
});
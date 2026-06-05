import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import { AlertURL, EventURL } from '../Endpoint/endpoint';


export const Eventusers = createAsyncThunk(
  'user/Eventusers',
  async () => {
    const response = await axios.get(`${AlertURL}`);
    return response.data;
  }
);

const Eventlist = createSlice({
  name: 'user',
  initialState: {
    users: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(Eventusers.pending, (state) => {
        state.loading = true;
      })
      .addCase(Eventusers.fulfilled, (state, action) => {
        state.loading = false;
        state.users = action.payload;
      })
      .addCase(Eventusers.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  },
});

export default Eventlist.reducer;
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import { EventURL } from '../Endpoint/endpoint';


export const fetchUsers = createAsyncThunk(
  'user/fetchUsers',
  async () => {
    const response = await axios.get(`${EventURL}`);
    return response.data;
  }
);

const userSlice = createSlice({
  name: 'user',
  initialState: {
    users: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUsers.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchUsers.fulfilled, (state, action) => {
        state.loading = false;
        state.users = action.payload;
      })
      .addCase(fetchUsers.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  },
});

export default userSlice.reducer;
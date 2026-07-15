import React from "react";
import { Box, useTheme, useMediaQuery } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";

const DataGridDemo = ({ columns, rows }) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));

  const responsiveColumns = columns.map((column) => ({
    ...column,
    flex: column.flex || 1,
    minWidth: column.minWidth || 120,
  }));

  return (
    <Box
      sx={{
        width: "100%",
        overflowX: "auto",
        "& .MuiDataGrid-root": {
          border: 1,
          borderColor: "divider",
        },
      }}
    >
      <DataGrid
        rows={rows}
        columns={responsiveColumns}
        autoHeight
        disableRowSelectionOnClick
        checkboxSelection={!isMobile}
        pageSizeOptions={[5, 10, 20]}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: isMobile ? 5 : 10,
            },
          },
        }}
        sx={{
          minWidth: "100%",
          "& .MuiDataGrid-columnHeaders": {
            backgroundColor: "#f5f5f5",
            fontWeight: "bold",
          },
        }}
      />
    </Box>
  );
};

export default DataGridDemo;
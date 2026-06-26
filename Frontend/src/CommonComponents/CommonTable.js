import { Box, useTheme, useMediaQuery } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";

export default function DataGridDemo({ columns, rows }) {

  const theme = useTheme();

  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));

  return (
    <Box
      sx={{
        width: "100%",
        height: isMobile ? 300 : 400,
      }}
    >
      <DataGrid
        rows={rows}
        columns={columns}
        autoHeight={isMobile}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: isMobile ? 3 : 5,
            },
          },
        }}
        pageSizeOptions={[3, 5, 10]}
        checkboxSelection={!isMobile}
        disableRowSelectionOnClick
      />
    </Box>
  );
}





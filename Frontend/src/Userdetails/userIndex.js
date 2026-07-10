import { Box, Button, IconButton, Tooltip } from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import { Styles } from "../CommonComponents/Commonstyles";
import Searchinput from "../CommonComponents/Searchinput";
import DataGridDemo from "../CommonComponents/CommonTable";
import { UserDetailslist } from "../Components/userdetailslist";
import { useNavigate } from "react-router-dom";
import DeleteIcon from "@mui/icons-material/Delete";

export default function UserDetails() {

    const handleviewclick = () => {
        navigate("/Viewdetails")
    }
    const columns = [
        { field: "id", headerName: "ID", width: 90 },
        {
            field: "Name",
            headerName: "Name",
            width: 150,
            editable: false,
        },
        {
            field: "email",
            headerName: "Email ID",
            width: 150,
            editable: false,
        },
        {
            field: "role",
            headerName: "Role",
            width: 150,
            editable: false,
        },
        {
            field: "mobile_no",
            headerName: "Mobile Number",
            width: 180,
            editable: false,
        },
        {
            field: "actions",
            headerName: "Actions",
            width: 150,
            sortable: false,
            filterable: false,
            align: "center",
            headerAlign: "center",
            renderCell: (params) => (
                <>
                    <Tooltip title="View Details">
                        <IconButton
                            color="primary"
                        // onClick={() => handleRoute(params.row)}
                        >
                            <VisibilityIcon />
                        </IconButton>
                    </Tooltip>

                    <Tooltip title="Delete User">
                        <IconButton
                            color="error"
                            sx={{ ml: 1 }}
                        // onClick={() => handleDelete(params.row)}
                        >=
                            <DeleteIcon />
                        </IconButton>
                    </Tooltip>
                </>
            ),
        },
    ];

    const rowsvalue = UserDetailslist;

    console.log("log the value here....", rowsvalue);

    const navigate = useNavigate();

    const handleButtonClick = () => {
        navigate("/addUser")
    }

    return (
        <>
            <Box
                sx={{
                    display: "flex",
                    alignItems: "center",
                    mb: 2,
                    width: "100%",
                }}
            >
                <Button
                    variant="contained"
                    color="success"
                    sx={{
                        ml: "auto",
                    }}
                    onClick={() => {
                        handleButtonClick();
                    }}
                >
                    ADD USER
                </Button>
            </Box>

            <div style={Styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by User Details"} />
                </div>

                <div style={Styles.tableContainer}>
                    <DataGridDemo
                        columns={columns}
                        rows={rowsvalue}
                    />
                </div>
            </div>
        </>
    );
}




import { useEffect, useState } from "react"
import { Styles } from "../../CommonComponents/Commonstyles"
import DataGridDemo from "../../CommonComponents/CommonTable"
import Searchinput from "../../CommonComponents/Searchinput"
import { userDataUrl } from "../../Endpoint/endpoint";
import VisibilityIcon from "@mui/icons-material/Visibility";
import IconButton from "@mui/material/IconButton";
import Tooltip from "@mui/material/Tooltip";
import DeleteIcon from "@mui/icons-material/Delete";
import { Box } from "@mui/system";
import { Button, TextField } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Dialogbox from "./dialogbox";


export default function Roles() {
    const [data, setData] = useState([]);
    const [view, setView] = useState(false);
    const [storeId, setStoreId] = useState();

    const response = async () => {
        try {
            const getData = await fetch(`${userDataUrl}/api/roles`);
            const APIData = await getData.json();
            if (APIData) {
                setData(APIData)
            }
        } catch (error) {
            console.log("log the error response here", error);
        }
    }
    useEffect(() => {
        response();
    }, []);

    const handleviewClick = (id) => {
        console.log("log the value here", id)
        setView(true)
        setStoreId(id?.id);
    }
    const columns = [
        {
            field: 'id',
            headerName: 'ID',
            width: 150,
            editable: false,
        },
        {
            field: "name",
            headerName: "Name",
            width: 150,
            editable: false
        },
        {
            field: 'actions',
            headerName: 'Actions',
            width: 100,
            sortable: false,
            filterable: false,
            align: 'center',
            headerAlign: 'center',
            renderCell: (params) => (
                <>
                    <Tooltip title="View Details">
                        <IconButton
                            color="primary"
                            onClick={() => handleviewClick(params.row)}
                        >
                            <VisibilityIcon />
                        </IconButton>
                    </Tooltip>
                    <Tooltip title="Delete User">
                        <IconButton
                            color="error"
                            sx={{ ml: 1 }}
                            disabled
                        >
                            <DeleteIcon />
                        </IconButton>
                    </Tooltip>
                </>
            ),
        },
    ]
    const navigate = useNavigate();

    const hanldeAddrole = () => {
        navigate("/Addnewrole")
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
                        hanldeAddrole();
                    }}
                >
                    ADD ROLE
                </Button>
            </Box>
            <div style={Styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by Role Name"} />
                </div>
                <div style={Styles.tableContainer}>
                    <DataGridDemo columns={columns} rows={data ? data : []} />
                </div>
                {view ? <Dialogbox storeId={storeId} setView ={setView} /> : ""}
            </div>
        </>
    )
}
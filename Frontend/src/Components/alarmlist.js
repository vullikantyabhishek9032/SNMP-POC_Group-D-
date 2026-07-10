import { useEffect, useState } from "react";
import DataGridDemo from "../CommonComponents/CommonTable";
import Searchinput from "../CommonComponents/Searchinput";
import { Todaydemo } from "../Endpoint/endpoint";
import { Box, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import VisibilityIcon from "@mui/icons-material/Visibility";
import IconButton from "@mui/material/IconButton";
import Tooltip from "@mui/material/Tooltip";
import {
    FormControl,
    InputLabel,
    Select,
    MenuItem
} from "@mui/material";

export default function Alaramlist() {
    const value = "14a9940e-b72b-468b-afb6-db4879db6a46"
    const handleRoute = (value) => {
        navigate(`/alerts/${value.alertId}`)
        // console.log("clicked row value to be seen here....",value)
    }

    const columns = [
        { field: 'id', headerName: 'ID', width: 90 },
        {
            field: 'hostname',
            headerName: 'Host Name',
            width: 150,
            editable: false,
        },
        {
            field: 'alertType',
            headerName: 'Alert Type',
            width: 150,
            editable: false,
        },
        {
            field: 'severity',
            headerName: 'Severity',
            width: 150,
            editable: false,
        },
        {
            field: 'alertTimestamp',
            headerName: 'Alert Timestamp',
            width: 180,
            editable: false,
        },
        {
            field: 'actions',
            headerName: 'Actions',
            width: 120,
            sortable: false,
            filterable: false,
            align: 'center',
            headerAlign: 'center',
            renderCell: (params) => (
                <Tooltip title="View Details">
                    <IconButton
                        color="primary"
                        onClick={() => handleRoute(params.row)}
                    >
                        <VisibilityIcon />
                    </IconButton>
                </Tooltip>
            ),
        },
    ];

    const [Apidata, setApiData] = useState();
    const [selected, setSelected] = useState("ALERTS")

    const APICall = async () => {
        const API = selected === "ALERTS" ? `${Todaydemo}/api/alerts` :
            selected === "CRITICAL ALERTS" ? `${Todaydemo}/api/alerts/severity/CRITICAL` :
                `${Todaydemo}/api/alerts`
        try {
            const response = await fetch(`${API}`);
            const saveResponse = await response.json();
            if (saveResponse) {
                setApiData(saveResponse);
            }
        } catch (error) {
            console.log(".......", error)
        }
    }
    useEffect(() => {
        APICall()
    }, [selected])
    const [filter, setFilter] = useState(Apidata);
    useEffect(() => {
        setFilter(Apidata)
    }, [Apidata])
    const onSearch = (e) => {
        const value = e.target.value.toLowerCase();

        if (!value) {
            setFilter(Apidata);
            return;
        }

        const filtered = Apidata?.filter((data) =>
        (data.hostname?.toLowerCase().includes(value) ||
            data.severity?.toLowerCase().includes(value) ||
            data.alertType?.toLowerCase().includes(value))
        );

        setFilter(filtered);
    };
    console.log("log the data here....", Apidata)

    const navigate = useNavigate();

    const [device, setDevice] = useState("");

    const handleChange = (event) => {
        setDevice(event.target.value);
        getDatabyhostname(event.target.value)
    };

    const getDatabyhostname = async (value) => {
        try {
            const response = await fetch(`${Todaydemo}/api/alerts/hostname/${value}`);
            const getResponse = await response.json();
            if (getResponse) {
                setApiData(getResponse);
            }
        } catch (error) {
            console.log("log the error in the console", error);
        }
    }
    // useEffect(() => {
    //     getDatabyhostname()
    // }, [device])
    return (
        <>
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    mb: 2,
                    flexWrap: "wrap",
                    gap: 2,
                }}
            >
                <Box
                    sx={{
                        display: "flex",
                        gap: 2,
                        flexWrap: "wrap",
                    }}
                >
                    <Button
                        variant={selected === "ALERTS" ? "contained" : "outlined"}
                        onClick={() => setSelected("ALERTS")}
                    >
                        ALERTS
                    </Button>

                    <Button
                        variant={selected === "CRITICAL ALERTS" ? "contained" : "outlined"}
                        onClick={() => setSelected("CRITICAL ALERTS")}
                    >
                        CRITICAL ALERTS
                    </Button>
                </Box>
                <Box
                    sx={{
                        display: "flex",
                        gap: 1,
                        flexWrap: "wrap",
                    }}
                >
                    <FormControl
                        sx={{
                            minWidth: 280,
                            "& .MuiOutlinedInput-root": {
                                borderRadius: "12px",
                                backgroundColor: "#fafafa",
                            },
                        }}
                    >
                        <InputLabel>Host Name</InputLabel>

                        <Select
                            value={device}
                            label="Device Type"
                            onChange={handleChange}
                        >
                            <MenuItem value="server1">server1</MenuItem>
                            <MenuItem value="Dummyserver1">Dummyserver1</MenuItem>
                            <MenuItem value="Dummyserver2">Dummyserver2</MenuItem>
                        </Select>
                    </FormControl>

                </Box>
            </Box>
            <div style={styles.container}>
                {Apidata?.length > 0 ?
                    <>
                        <div>
                            <Searchinput PlaceHolder={"Search by Host Name"} search={(e) => {
                                onSearch(e)
                            }} />
                        </div>
                        <div style={styles.tableContainer}>
                            <DataGridDemo
                                columns={columns}
                                rows={filter?.map((data, index) => {
                                    return {
                                        ...data,
                                        id: index + 1,
                                        alertTimestamp: new Date(data.alertTimestamp).toLocaleDateString(
                                            "en-GB",
                                            {
                                                day: "2-digit",
                                                month: "short",
                                                year: "numeric",
                                            }
                                        ),
                                    };
                                })}
                            />

                        </div>
                    </>

                    :
                    <>
                        <h2>No Data Found</h2>
                    </>
                }
            </div>
        </>
    );
}

const styles = {
    container: {
        padding: "20px",
        backgroundColor: "#f5f7fa",
    },

    searchBox: {
        display: "flex",
        gap: "12px",
        marginBottom: "30px",
    },

    input: {
        flex: 1,
        padding: "10px",
        borderRadius: "6px",
        border: "1px solid #ccc",
        outline: "none",
    },

    button: {
        padding: "10px 16px",
        backgroundColor: "#1976d2",
        color: "#fff",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
    },

    tableContainer: {
        marginTop: "10px",
        backgroundColor: "#fff",
        padding: "10px",
        borderRadius: "8px"
    }
};

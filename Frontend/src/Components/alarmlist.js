import { useEffect, useState } from "react";
import DataGridDemo from "../CommonComponents/CommonTable";
import { Margin } from "@mui/icons-material";
import Searchinput from "../CommonComponents/Searchinput";
import { useDispatch, useSelector } from "react-redux";
import { fetchUsers } from "../Redux/userSlice";
import { Alert } from "../Constants/alert";

export default function Alaramlist() {

    const columns = [
        { field: 'id', headerName: 'ID', width: 90 },
        {
            field: 'hostname',
            headerName: 'Host Name',
            width: 150,
            editable: true,
        },
        {
            field: 'alertType',
            headerName: 'Alert Type',
            width: 150,
            editable: true,
        },
        {
            field: 'severity',
            headerName: 'Severity',
            width: 150,
            editable: true,
        },
        {
            field: 'currentValue',
            headerName: 'Current Value',
            width: 150,
            editable: true,
        },
        {
            field: 'alertTimestamp',
            headerName: 'Alert Timestamp',
            width: 150,
            editable: true,
        },

    ];

    const [Apidata, setApiData] = useState();

    const APICall = async () => {
        try {
            const response = await fetch(``);
            const saveResponse = await response.json();
            if (saveResponse) {
                setApiData(saveResponse);
            }
        } catch (error) {
            console.log(".......", error)
        }
    }
    // useEffect(() => {
        // APICall()
    // },[])
    const [filter, setFilter] = useState(Alert);

    const onSearch = (e) => {
        const value = e.target.value.toLowerCase();

        if (!value) {
            setFilter(Alert);
            return;
        }

        const filtered = Alert.filter((data) =>
        (data.hostname?.toLowerCase().includes(value) ||
            data.alertType?.toLowerCase().includes(value))
        );

        setFilter(filtered);
    };

    return (
        <div style={styles.container}>
            <div>
                <Searchinput PlaceHolder={"Search by Host Name"} search={(e) => {
                    onSearch(e)
                }} />
            </div>
            <div style={styles.tableContainer}>
                <DataGridDemo
                    columns={columns}
                    rows={filter.map((data, index) => {
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

        </div>
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

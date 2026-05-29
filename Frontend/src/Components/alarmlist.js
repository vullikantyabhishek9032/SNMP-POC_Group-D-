import { useState } from "react";
import DataGridDemo from "../CommonComponents/CommonTable";
import { Margin } from "@mui/icons-material";

export default function Alaramlist() {
    const [Data, setData] = useState([]);

    const getResponse = async () => {
        try {
            const response = await fetch(``)
        } catch (error) {
            console.log("log the error.....")
        }
    }
    const columns = [
        { field: 'id', headerName: 'ID', width: 90 },
        {
            field: 'deviceName',
            headerName: 'Device Name',
            width: 150,
            editable: true,
        },
        {
            field: 'alarmName',
            headerName: 'Alarm Name',
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
            field: 'status',
            headerName: 'Status',
            width: 150,
            editable: true,
        },
        {
            field: 'createdTime',
            headerName: 'Created_AT',
            width: 150,
            editable: true,
        },

    ];

    const rows = [
        {
            id: 1, deviceName: 'Router-01', alarmName: 'Interface Down', severity: "CRITICAL", status: "ACTIVE",
            createdTime: new Date("2026-05-27 10:30:00").toLocaleDateString("en-GB", {
                day: "2-digit",
                month: "short",
                year: "numeric"
            })
        },
        {
            "id": 2,
            "deviceName": "Switch-02",
            "alarmName": "High CPU Usage",
            "severity": "MAJOR",
            "status": "ACTIVE",
            "sourceIp": "10.10.1.2",
            // "createdTime": "2026-05-27 10:40:00"
            createdTime: new Date("2026-05-27 10:40:00").toLocaleDateString("en-GB", {
                day: "2-digit",
                month: "short",
                year: "numeric"
            })
        },
        {
            "id": 3,
            "deviceName": "Firewall-01",
            "alarmName": "Link Restored",
            "severity": "MINOR",
            "status": "CLEARED",
            "sourceIp": "10.10.1.3",
            // "createdTime": "2026-05-27 11:00:00"
            createdTime: new Date("2026-05-27 11:00:00").toLocaleDateString("en-GB", {
                day: "2-digit",
                month: "short",
                year: "numeric"
            })
        },
        {
            "id": 4,
            "deviceName": "Router-02",
            "alarmName": "Packet Drop",
            "severity": "MAJOR",
            "status": "ACTIVE",
            "sourceIp": "10.10.1.4",
            // "createdTime": "2026-05-27 11:15:00"
            createdTime: new Date("2026-05-27 11:15:00").toLocaleDateString("en-GB", {
                day: "2-digit",
                month: "short",
                year: "numeric"
            })
        },
        {
            "id": 5,
            "deviceName": "Router-03",
            "alarmName": "Quick Check",
            "severity": "MAJOR",
            "status": "ACTIVE",
            "sourceIp": "10.10.1.4",
            // "createdTime": "2026-05-27 11:15:00"
            createdTime: new Date("2026-05-28 11:15:00").toLocaleDateString("en-GB", {
                day: "2-digit",
                month: "short",
                year: "numeric"
            })
        }
    ];

    const [filter, setFilter] = useState(rows);

    const onSearch = (e) => {
       
            const value = e.target.value;

            if (!value) {
                setFilter(rows);
                return
            }
            const filtered = rows.filter((data) => data.deviceName.toLowerCase().includes(value.toLowerCase()))
            setFilter(filtered);
    }

    return (
        <div style={styles.container}>

            <div style={styles.searchBox}>
                <input
                    type="text"
                    placeholder="Search by Device Name"
                    style={styles.input}
                    onChange={(e) => {
                        onSearch(e)
                    }}
                />
                {/* <button style={styles.button} onClick={() => {
                    onButtonClick()
                }}>Search</button> */}
            </div>

            <div style={styles.tableContainer}>
                <DataGridDemo columns={columns} rows={filter} />
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

import { useState, useEffect } from "react";
import { Todaydemo } from "../../Endpoint/endpoint";
import DataGridDemo from "../../CommonComponents/CommonTable";
import Searchinput from "../../CommonComponents/Searchinput";
import { Styles } from "../../CommonComponents/Commonstyles";
import { Metrics } from "../../Constants/metrics";
import { Box, Button } from "@mui/material";
import { CPUDevices } from "../../Constants/CPUDevices";
import { MemoryDevicelist } from "../../Constants/MemoryDevices";
import { useNavigate } from "react-router-dom";

export default function MetricsList() {
    const columns = [
        { field: "id", headerName: "ID", width: 90 },
        {
            field: "hostname",
            headerName: "Host Name",
            width: 150,
        },
        {
            field: "cpuUsage",
            headerName: "CPU Usage",
            width: 150,
        },
        {
            field: "memoryUsage",
            headerName: "Memory Usage",
            width: 150,
        },
        {
            field: "memoryAvailable",
            headerName: "Memory Available",
            width: 150,
        },
        {
            field: "metricTimestamp",
            headerName: "Metric Timestamp",
            width: 180,
        },
    ];
    const navigate = useNavigate();

    const [apiData, setApiData] = useState([]);
    const [selected, setSelected] = useState("DEVICES");
    const APICall = async () => {
        const Api = selected === "DEVICES" ? `${Todaydemo}/api/metrics` :
            selected === "CPU DEVICES" ? `${Todaydemo}/api/metrics/high-cpu` :
                selected === "MEMORY DEVICES" ? `${Todaydemo}/api/metrics/high-memory` : `${Todaydemo}/api/metrics`
        try {
            const response = await fetch(`${Api}`);
            const saveResponse = await response.json();

            if (saveResponse) {
                setApiData(saveResponse);
            }
        } catch (error) {
            console.log("Error:", error);
        }
    };

    useEffect(() => {
        APICall();
    }, [selected]);

    const rawData =
        selected === "DEVICES"
            ? Metrics
            : selected === "CPU DEVICES"
                ? CPUDevices
                : selected === "MEMORY DEVICES"
                    ? MemoryDevicelist
                    : Metrics;

    const [filter, setFilter] = useState(apiData);

    // Update table when tab changes
    useEffect(() => {
        setFilter(apiData);
    }, [apiData]);

    const onSearch = (e) => {
        const value = e.target.value.toLowerCase();

        if (!value) {
            setFilter(apiData);
            return;
        }

        const filteredData = apiData?.filter(
            (data) =>
                data.hostname?.toLowerCase().includes(value) ||
                data.cpuUsage?.toString().includes(value) ||
                data.memoryUsage?.toString().includes(value) ||
                data.memoryAvailable?.toString().includes(value)
        );

        setFilter(filteredData);
    };
    const AdddeviceClick = () => {
        navigate("/adddevices")
    }
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
                        variant={selected === "DEVICES" ? "contained" : "outlined"}
                        onClick={() => setSelected("DEVICES")}
                    >
                        DEVICES
                    </Button>

                    <Button
                        variant={selected === "CPU DEVICES" ? "contained" : "outlined"}
                        onClick={() => setSelected("CPU DEVICES")}
                    >
                        CPU DEVICES
                    </Button>

                    <Button
                        variant={selected === "MEMORY DEVICES" ? "contained" : "outlined"}
                        onClick={() => setSelected("MEMORY DEVICES")}
                    >
                        MEMORY DEVICES
                    </Button>
                </Box>
                <Box
                    sx={{
                        display: "flex",
                        gap: 2,
                        flexWrap: "wrap",
                    }}
                >
                    <Button
                        variant="contained"
                        color="success"
                        onClick={() => AdddeviceClick()}
                    >
                        ADD DEVICE
                    </Button>

                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={() => console.log("Upload Devices")}
                    >
                        UPLOAD DEVICES
                    </Button>
                </Box>
            </Box>
            {filter.length > 0 ?
                <div style={Styles.container}>
                    <div>
                        <Searchinput
                            PlaceHolder={"Search by values"}
                            search={onSearch}
                        />
                    </div>

                    <div style={Styles.tableContainer}>
                        <DataGridDemo
                            columns={columns}
                            rows={filter?.map((data, index) => ({
                                ...data,
                                id: index + 1,
                                metricTimestamp: data.metricTimestamp
                                    ? new Date(
                                        data.metricTimestamp
                                    ).toLocaleDateString("en-GB", {
                                        day: "2-digit",
                                        month: "short",
                                        year: "numeric",
                                    })
                                    : "",
                            }))}
                        />
                    </div>
                </div>
                : <h3>No data found</h3>}
        </>
    );
}


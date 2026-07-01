import { useState } from "react";
import { Todaydemo } from "../../Endpoint/endpoint";
import DataGridDemo from "../../CommonComponents/CommonTable";
import Searchinput from "../../CommonComponents/Searchinput";
import { Styles } from "../../CommonComponents/Commonstyles";
import { Metrics } from "../../Constants/metrics";

export default function MetricsList() {
    const columns = [
        { field: 'id', headerName: 'ID', width: 90 },
        {
            field: 'hostname',
            headerName: 'Host Name',
            width: 150,
            editable: true,
        },
        {
            field: 'cpuUsage',
            headerName: 'Cpu Usage',
            width: 150,
            editable: true,
        },
        {
            field: 'memoryUsage',
            headerName: 'Memory Usage',
            width: 150,
            editable: true,
        },
        {
            field: 'memoryAvailable',
            headerName: 'Memory Available',
            width: 150,
            editable: true,
        },
        {
            field: 'metricTimestamp',
            headerName: 'Metric Timestamp',
            width: 150,
            editable: true,
        },

    ];

    const [Apidata, setApiData] = useState();

    const APICall = async () => {
        try {
            const response = await fetch(`${Todaydemo}/api/metrics `);
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
    const [filter, setFilter] = useState(Metrics);

    const onSearch = (e) => {
        const value = e.target.value.toLowerCase();

        if (!value) {
            setFilter(Metrics);
            return;
        }

        const filtered = Metrics.filter((data) =>
        (data.hostname?.toLowerCase().includes(value) ||
            data.memoryAvailable?.toString().includes(value))
        );

        setFilter(filtered);
    };

    return (
        <>
            <div style={Styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by Host Name"} search={(e) => {
                        onSearch(e)
                    }} />
                </div>
                <div style={Styles.tableContainer}>
                    <DataGridDemo columns={columns} rows={filter.map((data, index) => {
                        return {
                            ...data,
                            id: index + 1,
                            metricTimestamp: new Date(data.metricTimestamp).toLocaleDateString(
                                "en-GB",
                                {
                                    day: "2-digit",
                                    month: "short",
                                    year: "numeric",
                                }
                            ),
                        };
                    })} />
                </div>

            </div>
        </>
    )
}
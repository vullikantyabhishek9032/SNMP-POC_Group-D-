import { useState } from "react";
import { Styles } from "../../CommonComponents/Commonstyles";
import DataGridDemo from "../../CommonComponents/CommonTable";
import Searchinput from "../../CommonComponents/Searchinput";
import { MemoryDevicelist } from "../../Constants/MemoryDevices";
import { Todaydemo } from "../../Endpoint/endpoint";


export default function Memorylist() {
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
            field: 'diskUsage',
            headerName: 'Disk Usage',
            width: 150,
            editable: true,
        },

    ];

    const [Apidata, setApiData] = useState();

    const APICall = async () => {
        try {
            const response = await fetch(`${Todaydemo}/api/metrics/high-memory`);
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
    const [filter, setFilter] = useState(MemoryDevicelist);

    const onSearch = (e) => {
        const value = e.target.value.toLowerCase();

        if (!value) {
            setFilter(MemoryDevicelist);
            return;
        }

        const filtered = MemoryDevicelist.filter((data) =>
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
                    <DataGridDemo columns={columns} rows={filter} />
                </div>

            </div>
        </>
    )
}
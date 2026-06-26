import { useState } from "react";
import { Styles } from "../../CommonComponents/Commonstyles"
import DataGridDemo from "../../CommonComponents/CommonTable"
import Searchinput from "../../CommonComponents/Searchinput"
import { CriticalAlert } from "../../Constants/Criticalalert";
import { Todaydemo } from "../../Endpoint/endpoint";


export default function Alramhistory() {
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
            const response = await fetch(`${Todaydemo}/api/alerts/severity/CRITICAL`);
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
    const [filter, setFilter] = useState(CriticalAlert);

    const onSearch = (e) => {
        const value = e.target.value.toLowerCase();

        if (!value) {
            setFilter(CriticalAlert);
            return;
        }

        const filtered = CriticalAlert.filter((data) =>
        (data.hostname?.toLowerCase().includes(value) ||
            data.alertType?.toLowerCase().includes(value))
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
                    <DataGridDemo columns={columns}
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
                        })} />
                </div>

            </div>
        </>
    )
}
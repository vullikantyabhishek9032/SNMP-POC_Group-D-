// export default function EventlistView(){
//     return (
//         <>
//         <h2>
//             ..............................
//         </h2>
//         </>
//     )
// }

import { useEffect, useState } from "react";
import { Styles } from "../../CommonComponents/Commonstyles";
import DataGridDemo from "../../CommonComponents/CommonTable";
import Searchinput from "../../CommonComponents/Searchinput";
import { eventsUrl } from "../../Endpoint/endpoint";

export default function EventlistView() {
    const columns = [
        { field: 'id', headerName: 'ID', width: 90 },
        {
            field: 'hostname',
            headerName: 'Host Name',
            width: 150,
            editable: true,
        },
        {
            field: 'memory_total',
            headerName: 'Total Memory',
            width: 150,
            editable: true,
        },
        {
            field: 'memory_used',
            headerName: 'Memory Utilized',
            width: 150,
            editable: true,
        },
        {
            field: 'memory_available',
            headerName: 'Available Memory',
            width: 150,
            editable: true,
        },
        {
            field: 'metric_timestamp',
            headerName: 'Created_AT',
            width: 150,
            editable: true,
        },

    ];
    const rows = [];

    const [data, setData] = useState();

    const response = async () => {
        try {
            const Api = await fetch(`${eventsUrl}/consumer`);
            const ResponseData = await Api.json();
            if (ResponseData) {
                setData(ResponseData)
            }
        } catch (error) {
            console.log("log it....", error);
        }
    }

    useEffect(() => {
        response()
    }, [])

    return (
        <>
            <div style={Styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by Event Name"} />
                </div>
                <div style={Styles.tableContainer}>
                    <DataGridDemo columns={columns} rows={data} />
                </div>
            </div>
        </>
    )
}

const styles = {
    tableContainer: {
        marginTop: "10px",
        backgroundColor: "#fff",
        padding: "10px",
        borderRadius: "8px"
    },
    container: {
        padding: "20px",
        backgroundColor: "#f5f7fa",
    },
}




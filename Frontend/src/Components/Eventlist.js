import DataGridDemo from "../CommonComponents/CommonTable";
import Searchinput from "../CommonComponents/Searchinput";

export default function Eventlist() {
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

    return (
        <>
            <div style={styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by Event Name"} />
                </div>
                <div style={styles.tableContainer}>
                    <DataGridDemo columns={columns} rows={rows} />
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




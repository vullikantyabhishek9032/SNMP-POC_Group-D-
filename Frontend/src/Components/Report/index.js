import { Styles } from "../../CommonComponents/Commonstyles"
import DataGridDemo from "../../CommonComponents/CommonTable"
import Searchinput from "../../CommonComponents/Searchinput"

export default function Planlist() {
    const columns = [
        { field: "id", headerName: "ID", width: 90 },
        {
            field: "plan_name",
            headerName: "Plan Name",
            width: 150,
        },
        {
            field: "data_limit",
            headerName: "Data Limit",
            width: 150,
        },
        {
            field: "plan_id",
            headerName: "Current Plan",
            width: 150,
        },
        {
            field: "benefits",
            headerName: "Benefits",
            width: 150,
        },
    ];
    const rows = []
    return (
        <>
            <div style={Styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by content"} />
                </div>
                <div style={Styles.tableContainer}>
                    <DataGridDemo columns={columns} rows={rows} />
                </div>

            </div>
        </>
    )
}
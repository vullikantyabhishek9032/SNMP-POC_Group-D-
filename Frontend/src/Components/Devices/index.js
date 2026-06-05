import { Styles } from "../../CommonComponents/Commonstyles";
import DataGridDemo from "../../CommonComponents/CommonTable";
import Searchinput from "../../CommonComponents/Searchinput";

export default function Devicelist() {
    const columns = [];
    const rows = [];

    return (
        <>
            <div style={Styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by Device Content"} />
                </div>
                <div style={Styles.tableContainer}>
                    <DataGridDemo columns={columns} rows={rows} />
                </div>

            </div>
        </>
    )
}
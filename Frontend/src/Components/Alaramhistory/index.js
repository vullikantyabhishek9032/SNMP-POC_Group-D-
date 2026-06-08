import { Styles } from "../../CommonComponents/Commonstyles"
import DataGridDemo from "../../CommonComponents/CommonTable"
import Searchinput from "../../CommonComponents/Searchinput"

export default function Alramhistory() {
    const columns = []
    const rows = []
    return (
        <>
            <div style={Styles.container}>
                <div>
                    <Searchinput PlaceHolder={"Search by History Events"}
                    // search={(e) => {
                    //     onSearch(e)
                    // }} 
                    />
                </div>
                <div style={Styles.tableContainer}>
                    <DataGridDemo columns={columns} rows={rows} />
                </div>

            </div>
        </>
    )
}
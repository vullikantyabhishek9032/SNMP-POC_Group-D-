
import { useEffect, useState } from "react";
import { Styles } from "../../CommonComponents/Commonstyles";
import DataGridDemo from "../../CommonComponents/CommonTable";
import Searchinput from "../../CommonComponents/Searchinput";
import { eventsUrl } from "../../Endpoint/endpoint";

export default function EventlistView() {
    const columns = [
        { field: 'id', headerName: 'ID', width: 90 },
        {
            field: 'customerId',
            headerName: 'Customer Id',
            width: 150,
            editable: true,
        },
        {
            field: 'currentPlan',
            headerName: 'Current Plan',
            width: 150,
            editable: true,
        },
        {
            field: 'recommendedPlan',
            headerName: 'recommendation plan',
            width: 150,
            editable: true,
        },
        {
            field: 'usage',
            headerName: 'Usage',
            width: 150,
            editable: true,
        },
        {
            field: 'benefits',
            headerName: 'Benifits',
            width: 150,
            editable: true,
        },
        {
            field: 'timestamp',
            headerName: 'DateAndTime',
            width: 150,
            editable: true,
        },
    ];
    const rows = [];

    const [data, setData] = useState();

    const response = async () => {
        try {
            const Api = await fetch(`${eventsUrl}/consumer/recommendations`);

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
                    <DataGridDemo columns={columns} 
                    // rows={data}
                      rows={data?.map((datas, index) => ({
                                ...datas,
                                id: index + 1,
                            }))}
                     />
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




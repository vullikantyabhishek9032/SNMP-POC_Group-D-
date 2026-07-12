import { TodayTwoTone } from "@mui/icons-material";
import { Box, Card, CardContent, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Todaydemo } from "../Endpoint/endpoint";

export default function GetAlarmbyId() {
    const { id } = useParams();

    const [ApiData, setApiData] = useState();

    const getData = async () => {
        try {
            const response = await fetch(`${Todaydemo}/api/alerts/${id}`);
            const storeResponse = await response.json();
            if (storeResponse) {
                setApiData(storeResponse)
            }
        } catch (error) {
            console.log("log the error here....", error);
        }
    }
    useEffect(() => {
        getData();
    }, []);

    console.log("log the id value", ApiData)

    return (
        <>
            <Card
                sx={{
                    width: { xs: "100%", sm: 450 },
                    borderRadius: 2,
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    p: 2,
                    ml: 25
                }}
            >
                {ApiData ?
                    <CardContent sx={{ p: 4 }}>
                        <Box textAlign="center" mb={4}>
                            <Typography
                                variant="h4"
                                fontWeight="bold"
                                color="primary"
                                gutterBottom
                            >
                                Alert Detailed View
                            </Typography>
                        </Box>
                        <Typography sx={{ mb: 2 }}>
                            <strong>AlertId :</strong> {ApiData.alertId || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Host Name :</strong> {ApiData.hostname || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Alert Type :</strong> {ApiData.alertType || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Severity :</strong> {ApiData.severity || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Message :</strong> {ApiData.message || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Current Value :</strong> {ApiData.currentValue || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Threshold Value :</strong> {ApiData.thresholdValue || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Alert Timestamp :</strong> {new Date(ApiData.alertTimestamp).toLocaleDateString(
                                "en-GB",
                                {
                                    day: "2-digit",
                                    month: "short",
                                    year: "numeric",
                                }
                            ) || "-"}
                        </Typography>
                        <Typography sx={{ mb: 2 }}>
                            <strong>Metric ID :</strong> {ApiData.metricId || "-"}
                        </Typography>

                    </CardContent> : <h2>No Data Found</h2>}
            </Card>
        </>
    )
}
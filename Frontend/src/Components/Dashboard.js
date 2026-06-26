import React, { useEffect, useState } from "react";
import {
    Box,
    Card,
    CardActionArea,
    CardContent,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Paper,
    IconButton,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";

export default function SelectActionCard() {
    const [data, setData] = useState({
        totalAlerts: 13,
        criticalAlerts: 8,
        highCpuDevices: 10,
        highMemoryDevices: 11
    }
    )
    const cards = [
        { id: 1, title: "Alerts", description: data.totalAlerts },
        { id: 2, title: "Critical Alerts", description: data.criticalAlerts },
        { id: 3, title: "Cpu Devices", description: data.highCpuDevices },
        { id: 4, title: "Memory Devices", description: data.highMemoryDevices },
    ];
    const [selectedCard, setSelectedCard] = React.useState(0);
    const navigate = useNavigate();

    const handleCardClick = (index) => {
        const routes = ["/alarms/active", "/alarms/history", "/devices/cpu", "/devices/memory"];
        navigate(routes[index]);
    };

    // useEffect(() => {
    //     setData()
    // }, [])

    return (
        <Box sx={{ p: { xs: 1, sm: 2, md: 3 } }}>

            <Box
                sx={{
                    display: "grid",
                    gridTemplateColumns: {
                        xs: "1fr",          // mobile
                        sm: "repeat(2, 1fr)", // tablet
                        md: "repeat(4, 1fr)", // desktop
                    },
                    gap: 2,
                }}
            >
                {cards.map((card, index) => (
                    <Card
                        key={card.id}
                        sx={{
                            borderRadius: 3,
                            background:
                                selectedCard === index
                                    ? "linear-gradient(135deg, #1976d2, #42a5f5)"
                                    : "#fff",
                            color: selectedCard === index ? "#fff" : "inherit",
                            transition: "0.3s",
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "space-between",
                        }}
                    >
                        <CardActionArea onClick={() => setSelectedCard(index)}>
                            <CardContent>
                                <Typography
                                    variant="h6"
                                    sx={{ fontSize: { xs: 16, md: 18 } }}
                                >
                                    {card.title}
                                </Typography>
                                <Typography
                                    variant="body1"
                                    sx={{ fontSize: { xs: 14, md: 16 } }}
                                >
                                    {card.description}
                                </Typography>
                            </CardContent>
                        </CardActionArea>

                        <IconButton
                            sx={{ alignSelf: "flex-end" }}
                            onClick={(e) => {
                                e.stopPropagation();
                                handleCardClick(index);
                            }}
                        >
                            <ArrowForwardIcon />
                        </IconButton>
                    </Card>
                ))}
            </Box>

            <Box
                sx={{
                    mt: 3,
                    display: "grid",
                    gridTemplateColumns: {
                        xs: "1fr",        // mobile
                        md: "30% 70%",   // desktop
                    },
                    gap: 2,
                }}
            >

                <Paper elevation={3}>
                    <Typography sx={{ p: 2, fontWeight: "bold", fontSize: { xs: 14, md: 16 } }}>
                        Logs Info
                    </Typography>

                    <Box sx={{ overflowX: "auto" }}>
                        <Table size="small">
                            <TableHead>
                                <TableRow>
                                    <TableCell><b>ID</b></TableCell>
                                    <TableCell><b>Log Name</b></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {[
                                    { id: 1, logName: "System Logs" },
                                    { id: 2, logName: "Error Logs" },
                                ].map((row) => (
                                    <TableRow key={row.id}>
                                        <TableCell>{row.id}</TableCell>
                                        <TableCell>{row.logName}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Box>

                    <Box sx={{ display: "flex", justifyContent: "flex-end", p: 1 }}>
                        <IconButton
                            onClick={() => navigate("/events/logs")}
                            sx={{
                                backgroundColor: "#1976d2",
                                color: "#fff",
                                "&:hover": { backgroundColor: "#1565c0" },
                            }}
                        >
                            <ArrowForwardIcon />
                        </IconButton>
                    </Box>
                </Paper>

                <Paper elevation={3}>
                    <Typography sx={{ p: 2, fontWeight: "bold", fontSize: { xs: 14, md: 16 } }}>
                        Performance Dashboard
                    </Typography>

                    <Box sx={{ overflowX: "auto" }}>
                        <Table size="small">
                            <TableHead>
                                <TableRow>
                                    <TableCell><b>ID</b></TableCell>
                                    <TableCell><b>Name</b></TableCell>
                                    <TableCell><b>Available %</b></TableCell>
                                    <TableCell><b>Utilized</b></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {[
                                    { id: 1, name: "CPU", available: "40%", utilized: "60%" },
                                    { id: 2, name: "Memory", available: "30%", utilized: "70%" },
                                ].map((row) => (
                                    <TableRow key={row.id}>
                                        <TableCell>{row.id}</TableCell>
                                        <TableCell>{row.name}</TableCell>
                                        <TableCell>{row.available}</TableCell>
                                        <TableCell>{row.utilized}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Box>

                    <Box sx={{ display: "flex", justifyContent: "flex-end", p: 1 }}>
                        <IconButton
                            onClick={() => navigate("/performance")}
                            sx={{
                                backgroundColor: "#1976d2",
                                color: "#fff",
                                "&:hover": { backgroundColor: "#1565c0" },
                            }}
                        >
                            <ArrowForwardIcon />
                        </IconButton>
                    </Box>
                </Paper>
            </Box>
        </Box>
    );
}
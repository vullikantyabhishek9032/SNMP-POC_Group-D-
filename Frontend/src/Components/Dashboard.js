
import React from "react";
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
    Button,
} from "@mui/material";
import { IconButton } from "@mui/material";
import { useNavigate } from "react-router-dom";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";

const cards = [
    {
        id: 1,
        title: 'Alarms',
        description: "5",
    },
    {
        id: 2,
        title: 'Events',
        description: "4",
    },
    {
        id: 3,
        title: 'Performance',
        description: '0',
    },
    {
        id: 4,
        title: "Devices",
        description: "0"
    }
];

export default function SelectActionCard() {

    const [selectedCard, setSelectedCard] = React.useState(0);

    const navigate = useNavigate();

    const handleCardClick = (index) => {
        if (index === 0) {
            navigate("/alarms/active")
        } else if (index === 1) {
            navigate("/events/list")
        } else if (index === 2) {
            navigate("/performance")
        } else if (index === 3) {
            navigate("/devices")
        }
        // setSelectedCard(index)
    }

    return (
        <Box sx={{ padding: 3 }}>

            <Box
                sx={{
                    width: "100%",
                    display: "grid",
                    gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))",
                    gap: 2,
                }}
            >
                {cards.map((card, index) => (
                    <Card
                        key={card.id}
                        sx={{
                            borderRadius: 3,
                            border: "2px solid transparent",
                            background:
                                selectedCard === index
                                    ? "linear-gradient(135deg, #1976d2, #42a5f5)"
                                    : "#fff",
                            color: selectedCard === index ? "#fff" : "inherit",
                            transition: "all 0.3s ease",
                            boxShadow:
                                selectedCard === index
                                    ? "0px 8px 20px rgba(25, 118, 210, 0.5)"
                                    : "0px 2px 8px rgba(0,0,0,0.1)",

                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "space-between",

                        }}
                    >
                        <CardActionArea onClick={() => setSelectedCard(index)}>
                            <CardContent>
                                <Typography variant="h6">{card.title}</Typography>
                                <Typography variant="body2">
                                    {card.description}
                                </Typography>
                            </CardContent>
                        </CardActionArea>

                        <IconButton
                            sx={{
                                alignSelf: "flex-end",
                            }}
                            onClick={(e) => {
                                e.stopPropagation();
                                handleCardClick(index)
                            }}
                        >
                            <ArrowForwardIcon />
                        </IconButton>
                    </Card>
                ))}
            </Box>
            <Box
                sx={{
                    marginTop: 4,
                    display: "grid",
                    gridTemplateColumns: "30% 70%",
                    gap: 2,
                }}
            >

                <Paper elevation={3}>
                    <Typography sx={{ p: 2, fontWeight: "bold" }}>
                        Logs Info
                    </Typography>
                    <Table>
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
                            ].slice(0, 4).map((row) => (
                                <TableRow key={row.id}>
                                    <TableCell>{row.id}</TableCell>
                                    <TableCell>{row.logName}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>

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
                    <Typography sx={{ p: 2, fontWeight: "bold" }}>
                        Performance Dashboard
                    </Typography>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><b>ID</b></TableCell>
                                <TableCell><b>Performance Name</b></TableCell>
                                <TableCell><b>Available %</b></TableCell>
                                <TableCell><b>Utilized</b></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {[
                                {
                                    id: 1,
                                    name: "CPU",
                                    available: "40%",
                                    utilized: "60%",
                                },
                                {
                                    id: 2,
                                    name: "Memory",
                                    available: "30%",
                                    utilized: "70%",
                                },
                            ].slice(0, 4).map((row) => (
                                <TableRow key={row.id}>
                                    <TableCell>{row.id}</TableCell>
                                    <TableCell>{row.name}</TableCell>
                                    <TableCell>{row.available}</TableCell>
                                    <TableCell>{row.utilized}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
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

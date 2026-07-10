import * as React from "react";
import {
    Box,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemText,
    Collapse,
    Divider,
    Toolbar,
    AppBar,
    Typography,
} from "@mui/material";
import ExpandLess from "@mui/icons-material/ExpandLess";
import ExpandMore from "@mui/icons-material/ExpandMore";
import { useNavigate } from "react-router-dom";

const drawerWidth = 200;

export default function PermanentDrawerLeft({ children }) {
    const [openDropdown, setOpenDropdown] = React.useState(null);
    const navigate = useNavigate();

    const handleToggle = (item) => {
        setOpenDropdown(openDropdown === item ? null : item);
    };

    const menu = [
        { title: "Dashboard", path: "/dashboard" },
        { title: "Metric Devices", path: "/metrics" },
        { title: "Alerts", path: "/alerts" },
        { title: "Events", path: "/events" },
        { title: "User Details", path: "/user" },
        { title: "Performance", path: "/performance" },
        { title: "Roles", path: "/roles" },
        { title: "Plan Manager", path: "/avaviableplans" },
        { title: "Settings", path: "/settings" },
    ];

    return (
        <Box sx={{ display: "flex" }}>
            {/* 🔹 AppBar */}
            <AppBar
                position="fixed"
                sx={{
                    width: `calc(100% - ${drawerWidth}px)`,
                    ml: `${drawerWidth}px`,
                    backgroundColor: "#10B981",
                }}
            >
                <Toolbar>
                    <Typography variant="h6">
                        Network Management System
                    </Typography>
                </Toolbar>
            </AppBar>

            {/* 🔹 Drawer */}
            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    "& .MuiDrawer-paper": {
                        width: drawerWidth,
                        boxSizing: "border-box",
                    },
                }}
            >
                <Toolbar />

                <Divider />

                <List>
                    {menu.map((item) => (
                        <React.Fragment key={item.title}>

                            {/* 🔹 Main Item */}
                            <ListItem disablePadding>
                                <ListItemButton
                                    onClick={() => {
                                        if (item.children) {
                                            handleToggle(item.title);
                                        } else {
                                            navigate(item.path);
                                        }
                                    }}
                                >
                                    <ListItemText primary={item.title} />

                                    {/* Dropdown icon */}
                                    {item.children &&
                                        (openDropdown === item.title ? (
                                            <ExpandLess />
                                        ) : (
                                            <ExpandMore />
                                        ))}
                                </ListItemButton>
                            </ListItem>

                            {/* 🔹 Dropdown */}
                            {item.children && (
                                <Collapse
                                    in={openDropdown === item.title}
                                    timeout="auto"
                                    unmountOnExit
                                >
                                    <List component="div" disablePadding>
                                        {item.children.map((sub) => (
                                            <ListItem key={sub.label} sx={{ pl: 4 }} disablePadding>
                                                <ListItemButton onClick={() => navigate(sub.path)}>
                                                    <ListItemText primary={sub.label} />
                                                </ListItemButton>
                                            </ListItem>
                                        ))}
                                    </List>
                                </Collapse>
                            )}
                        </React.Fragment>
                    ))}
                </List>
            </Drawer>

            {/* 🔹 Main Content */}
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <Toolbar />
                {children}
            </Box>
        </Box>
    );
}
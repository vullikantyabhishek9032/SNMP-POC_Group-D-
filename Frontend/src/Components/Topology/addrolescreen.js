import { Box, Button, Card, CardContent, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { userDataUrl } from "../../Endpoint/endpoint";
import { useNavigate } from "react-router-dom";

export default function Addnewrole() {
    const [role, setRole] = useState("");

    const hanldeChange = (e) => {
        setRole(e.target.value)
    }
    const navigate = useNavigate();

    const onSubmit = async () => {
        if (role === "" || role === null) {
            alert("Role Name should not be empty")
        }
        const reqObj = {
            name: role
        }
        const submitResponse = await fetch(
            `${userDataUrl}/api/roles`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(reqObj),
            }
        );

        const responseData = await submitResponse.json();

        if (responseData) {
            alert("Role Created sccessfully");
            navigate("/roles")
        }

    }
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
                <CardContent sx={{ p: 4 }}>
                    <Box textAlign="center" mb={4}>
                        <Typography
                            variant="h4"
                            fontWeight="bold"
                            color="primary"
                            gutterBottom
                        >
                            Add New Role
                        </Typography>
                    </Box>
                    <TextField
                        fullWidth
                        label="Role Name"
                        placeholder="Role Name"
                        margin="normal"
                        onChange={(e) => {
                            hanldeChange(e)
                        }}
                    />
                    <Button
                        variant="contained"
                        size="large"
                        onClick={() => {
                            onSubmit()
                        }}
                        sx={{
                            mt: 3,
                            py: 1.5,
                            ml: 17,
                            borderRadius: 2,
                            fontWeight: "bold",
                            textTransform: "none",
                            fontSize: "16px",
                        }}
                    >
                        Submit
                    </Button>
                </CardContent>
            </Card>
        </>
    )
}
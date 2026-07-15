import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useEffect, useState } from 'react';
import { Button, TextField } from '@mui/material';
import { userDataUrl } from '../../Endpoint/endpoint';

export default function Dialogbox({ storeId, setView }) {
    console.log("..........", storeId)

    const [open, setOpen] = useState(true);

    const handleClose = () => {
        setOpen(false);
        setView(false)
    };

    const [data, setData] = useState();

    const responseView = async () => {
        try {
            const getResponse = await fetch(`${userDataUrl}/api/roles/${storeId}`);
            const storeResponse = await getResponse.json();
            if (storeResponse) {
                setData(storeResponse)
            }
        } catch (error) {
            console.log("error to be seen in console", error);
        }
    }

    useEffect(() => {
        if (!storeId) {
            return ""
        } else {
            responseView()
        }
    }, [])
    console.log("view the store Id here....", data)
    return (
        <>
            <Dialog
                open={open}
                onClose={(event, reason) => {
                    if (reason === "backdropClick" || reason === "escapeKeyDown") {
                        return;
                    }
                    handleClose();
                }}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                role="alertdialog"
                disableEscapeKeyDown
            >
                <DialogTitle id="alert-dialog-title">
                    {"View/Edit Role Name"}
                </DialogTitle>

                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        <TextField
                            fullWidth
                            label="Role Name"
                            placeholder="Role Name"
                            margin="normal"
                            value={data ? data.name : ""}
                            disabled
                       
                        />
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus disabled>
                        Submit
                    </Button>
                    <Button onClick={() => {
                        handleClose()
                    }}>
                        Cancel
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    )
}
import React, {useEffect, useState} from 'react';
import {ThemeProvider, withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import './UserPersonalPage.css';
import PropTypes from 'prop-types';

import {appTheme, baseStyles} from "../api/styles-template";
import _ from "lodash";

import {styled} from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Button from "@material-ui/core/es/Button/Button";
import {SelectableList} from "./subcomponents/SelectableList";
import {fetchFriends, removeFriend, saveFriends} from "../api/api";
import DeleteIcon from '@mui/icons-material/Delete';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};


const Item = styled(Paper)(({theme}) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

const UserPersonalPageComponent = (props) => {
    const {classes, userData} = props;
    const [open, setOpen] = useState(false);
    const [selectedNames, setselectedNames] = useState([]);
    const [friends, setFriends] = useState({users: [], isFetching: false});

    const loadFriends = () => {
        fetchFriends(friends, setFriends)
    }

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleRemoveFriend = (login) => {
        removeFriend(login, loadFriends)
        console.log(login)
    }

    const handleSaveAndClose = () => {
        saveFriends(selectedNames, loadFriends)
        setOpen(false);
    };

    useEffect(() => {
        loadFriends()
    }, []);
    return (

        <Box data-node="user-personal-page" sx={{flexGrow: 1}}>
            <ThemeProvider theme={appTheme}>
                <Grid container spacing={3}>
                    <Grid item xs>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h4" color="inherit" className={classes.grow}>
                                    Личные данные
                                </Typography>
                            </ThemeProvider>
                        </Item>
                    </Grid>
                    <Grid item xs>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h4" color="inherit" className={classes.grow}>
                                    Друзья
                                </Typography>
                            </ThemeProvider>
                        </Item>
                    </Grid>
                    <Grid item xs={4}>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h4" color="inherit" className={classes.grow}>
                                    Интересы
                                </Typography>
                            </ThemeProvider>
                        </Item>
                    </Grid>
                </Grid>
                <br/>
                <Grid container spacing={3}>
                    <Grid item xs>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h6" color="inherit" className={classes.grow}>
                                    Логин: {_.get(userData, 'currentUser.login')}
                                </Typography>
                            </ThemeProvider>
                        </Item>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h6" color="inherit" className={classes.grow}>
                                    Имя: {_.get(userData, 'currentUser.firstName')}
                                </Typography>
                            </ThemeProvider>
                        </Item>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h6" color="inherit" className={classes.grow}>
                                    Фамилия: {_.get(userData, 'currentUser.lastName')}
                                </Typography>
                            </ThemeProvider>
                        </Item>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h6" color="inherit" className={classes.grow}>
                                    Пол: {_.get(userData, 'currentUser.gender')}
                                </Typography>
                            </ThemeProvider>
                        </Item>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h6" color="inherit" className={classes.grow}>
                                    Возраст: {_.get(userData, 'currentUser.age')}
                                </Typography>
                            </ThemeProvider>
                        </Item>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h6" color="inherit" className={classes.grow}>
                                    Живу в: {_.get(userData, 'currentUser.city')}
                                </Typography>
                            </ThemeProvider>
                        </Item>
                    </Grid>
                    <Grid item xs>
                        <Item>
                            {!open && <Button variant="contained" onClick={handleClickOpen}>Добавить</Button>}
                            {open && <Button onClick={handleClose}>Отмена</Button>}
                            {open && <Button onClick={handleSaveAndClose}>Сохранить</Button>}
                            {open &&
                            <SelectableList selectedNames={selectedNames} setSelectedNamesHandler={setselectedNames}/>}
                        </Item>
                        <Item>
                        </Item>
                        {!open && friends && friends.users.map((user) => (
                            <Item key={user.login}>
                                {user.firstName + " " + user.lastName}
                                <DeleteIcon onClick={() => handleRemoveFriend(user.login)}/>
                            </Item>
                        ))}
                    </Grid>
                    <Grid item xs={4}>
                        <Item>
                            <ThemeProvider theme={appTheme}>
                                <Typography variant="h4" color="inherit" className={classes.grow}>
                                    {_.get(userData, 'currentUser.personal')}
                                </Typography>
                            </ThemeProvider>
                        </Item>
                    </Grid>
                </Grid>
            </ThemeProvider>
        </Box>

    );
};

UserPersonalPageComponent.propTypes = {
    classes: PropTypes.object.isRequired,
};

export const UserPersonalPage = withStyles(baseStyles)(UserPersonalPageComponent);
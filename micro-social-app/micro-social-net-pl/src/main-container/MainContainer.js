import React, {useEffect, useState} from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import {ThemeProvider} from '@material-ui/core/styles';
import {withStyles} from '@material-ui/core/styles';
import './MainContainer.css';
import {TopStyledBar} from '../button-app-bar/ButtonAppBar';
import PropTypes from 'prop-types';
import {fetchUserData} from "../api/api";
import {appTheme, baseStyles} from "../api/styles-template";
import {UserPersonalPage} from "../user-personal-page/UserPersonalpage";

const MainContainerComponent = (props) => {
    let [userData, setUserData] = useState({currentUser: {}, users: [], isFetching: false});
    useEffect(() => {fetchUserData(userData, setUserData);}, []);
    return (
        <div data-node="main-container" className="mainContainer">
            <ThemeProvider theme={appTheme}>
                <CssBaseline/>
                <TopStyledBar userData={userData}/>
                <UserPersonalPage userData={userData}/>
            </ThemeProvider>
        </div>
    );
};

MainContainerComponent.propTypes = {
    classes: PropTypes.object.isRequired,
};

export const MainContainer = withStyles(baseStyles)(MainContainerComponent);
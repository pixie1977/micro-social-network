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
import {UserSearch} from "../search/UserSearch";
import {HotNewsWebsocket} from "../hot-news/HotNewsWebsocket";

const MainContainerComponent = (props) => {
    let [userData, setUserData] = useState({currentUser: {}, users: [], isFetching: false});
    let [uiState, setUiState] = useState(0);
    useEffect(() => {fetchUserData(userData, setUserData);}, []);
    return (
        <div data-node="main-container" className="mainContainer">
            <ThemeProvider theme={appTheme}>
                <CssBaseline/>
                <TopStyledBar userData={userData} uiState={uiState} setUiState={setUiState}/>
                {uiState===0 && <UserPersonalPage userData={userData}/>}
                {uiState===1 && <UserSearch/>}
                <HotNewsWebsocket/>
            </ThemeProvider>
        </div>
    );
};

MainContainerComponent.propTypes = {
    classes: PropTypes.object.isRequired,
};

export const MainContainer = withStyles(baseStyles)(MainContainerComponent);
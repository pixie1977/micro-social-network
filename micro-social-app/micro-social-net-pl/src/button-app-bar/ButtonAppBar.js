import React from 'react';
import PropTypes from 'prop-types';
import 'regenerator-runtime/runtime'
import {withStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

import 'react-popper-tooltip/dist/styles.css';
import './ButtonAppBar.css';
import {baseStyles} from "../api/styles-template";
import _ from "lodash";


const ButtonAppBar = (props) => {
    const {classes, userData} = props;
    return (
        <div className={classes.root}>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" color="inherit" className={classes.grow}>
                        Micro Social Net )(
                    </Typography>

                    <Typography variant="h4" color="inherit" className={classes.grow}>
                        {_.get(userData, 'currentUser.firstName')}
                    </Typography>

                    <Button
                        color="inherit"
                        onClick={() => {
                            window.location.href = '/logout'
                        }}
                    >
                        Выход
                    </Button>
                </Toolbar>
            </AppBar>
        </div>
    );
};

ButtonAppBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export const TopStyledBar = withStyles(baseStyles)(ButtonAppBar);

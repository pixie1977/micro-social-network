import createTheme from "@material-ui/core/es/styles/createTheme";
import {red} from '@material-ui/core/colors';


export const baseStyles = {
    root: {
        flexGrow: 1,
        minWidth: 300,
    },
    grow: {
        flexGrow: 1,
        textAlign: 'left',
    },
    menuButton: {
        marginLeft: -12,
        marginRight: 20,
    },
};

// A custom theme for this app
export const appTheme = createTheme({
    palette: {
        primary: {
            main: '#556cd6',
        },
        secondary: {
            main: '#19857b',
        },
        error: {
            main: red.A400,
        },
        background: {
            default: 'white',
        },
    },
});
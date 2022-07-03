import React, {useState} from "react";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import TextField from "@mui/material/TextField";
import {withStyles} from '@material-ui/core/styles';
import './UserSearch.css';
import PropTypes from 'prop-types';
import {baseStyles} from "../api/styles-template";
import {searchUsers} from "../api/api";

const UserSearchComponent = (props) => {
    const {classes, userData,} = props;
    const [criteria, setCriteria] = useState("1");
    const [searchItem, setSearchItem] = useState({users: [], isFetching: false});
    const [searchQuery, setSearchQuery] = useState({firstName: "_", lastName: "_"});

    const changeSearchQueryHandler = (e) => {
        const value = e.target.value.toString();
        if (value.length > 3) {
            const trimmedValue = value.trim();
            const re = ' ';
            const splitted = trimmedValue.split(re);
            const firstName = splitted[0];
            const lastName = splitted[splitted.length - 1];
            setSearchQuery({...searchQuery, firstName: firstName, lastName: lastName});
        }
    }

    const searchUsersHandler = () => {
        console.log(searchQuery);
        searchUsers(searchItem, searchQuery, setSearchItem);
    }

    return (
        <div>
            <div className="padded-div">
                <div className="text">
                    <TextField
                        id="search-bar"
                        className="text"
                        onChange={changeSearchQueryHandler}
                        label="Введите имя и фамилию через пробел"
                        variant="outlined"
                        placeholder="Search..."
                        size="small"
                    />
                    <IconButton type="submit"
                                aria-label="search"
                                onClick={searchUsersHandler}>
                        <SearchIcon style={{fill: "white"}}/>
                    </IconButton>
                </div>
            </div>
            <div>
                <ul>
                    {searchItem.users}
                </ul>
            </div>
        </div>
    );
};

UserSearchComponent.propTypes = {
    classes: PropTypes.object.isRequired,
};

export const UserSearch = withStyles(baseStyles)(UserSearchComponent);
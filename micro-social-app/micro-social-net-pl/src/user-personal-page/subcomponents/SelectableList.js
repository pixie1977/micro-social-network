import React, {useState, useEffect} from 'react';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import {fetchNotFriends} from "../../api/api";
import {withStyles} from '@material-ui/core/styles';
import {baseStyles} from "../../api/styles-template";
import _ from "lodash";

const SelectableListComponent = (props) => {
    const {selectedNames, setSelectedNamesHandler} = props;
    const [names, setNames] = useState({names:[], isFetching: false});
    useEffect(() => {fetchNotFriends(names, setNames);}, []);
    const handleChangeMultiple = (event) => {
        const { options } = event.target;
        const value = [];
        for (let i = 0, l = options.length; i < l; i += 1) {
            if (options[i].selected) {
                value.push(options[i].value);
            }
        }
        setSelectedNamesHandler(value);
    };
    let currentNames = _.get(names, 'names', [])
    return (
        <div>
            <FormControl sx={{ m: 1, minWidth: 60, maxWidth: 300 }}>
                <InputLabel shrink htmlFor="select-multiple-native">
                    Доступны к добавлению
                </InputLabel>
                <Select
                    multiple
                    native
                    value={selectedNames}
                    onChange={handleChangeMultiple}
                    label="Доступны к добавлению"
                    inputProps={{
                        id: 'select-multiple-native',
                    }}
                >
                    {currentNames && currentNames.map((user) => (
                        <option key={user.id} value={user.login}>
                            {user.firstName+" "+user.lastName}
                        </option>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}

export const SelectableList = withStyles(baseStyles)(SelectableListComponent);
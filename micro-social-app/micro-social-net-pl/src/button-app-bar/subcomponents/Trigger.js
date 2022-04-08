import React from 'react';
import {cornerMark, roundPicture} from "./svg";
import './Trigger.css';

export const Trigger = ({getTriggerProps, triggerRef}) => {
    const localProps = {
        ...getTriggerProps({
            ref: triggerRef,
            className: 'trigger',
        })
    };

    return (
        <div
            {...localProps}
            onClick={(event) => {
                localProps.onClick(event);
            }}
            data-node='trigger'
        >
            {roundPicture}

            <div className='pointer'>
                {cornerMark}
            </div>
        </div>
    );
};

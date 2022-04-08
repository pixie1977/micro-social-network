import React from 'react';

export const CustomTooltip = (setAuthorizedHandler) => {
    return function ({
                         getTooltipProps,
                         getArrowProps,
                         tooltipRef,
                         arrowRef,
                         placement,
                     }) {
        return (
            <div
                {...getTooltipProps({
                    ref: tooltipRef,
                    className: 'tooltip-container roundSquare',
                })}
            >
                <div
                    {...getArrowProps({
                        ref: arrowRef,
                        'data-placement': placement,
                        className: 'tooltip-arrow',
                    })}
                />
                <div className="tooltip-body">
                    <div className="tooltipItem">Profile</div>
                    <div
                        className="tooltipItem"
                        onClick={() => {
                            setAuthorizedHandler(false);
                        }}
                    >
                        Log Out
                    </div>
                </div>
            </div>
        );
    };
};

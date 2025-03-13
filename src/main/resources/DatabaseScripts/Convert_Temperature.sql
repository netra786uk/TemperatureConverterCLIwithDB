/***********************************************************************
*  Procedure: Convert_Temperature
*  Description:
*      This program takes a temperature value and its unit (either 
*      Fahrenheit or Celsius), and then converts it to the opposite unit. 
*      Use 'F' for Fahrenheit and 'C' for Celsius.
*
*  Author: Neetu Mishra
*  Version: 1.0
*  Date: [10-Mar-2025]
*  Comment: Initial Version
***********************************************************************/

/* 
  Change History:
  Version    |  Author      | Date       | Description
  -----------|--------------|------------|-----------------------------------------------------
  1.0        | Neetu Mishra | [10-Mar-2025]     | Initial version.
*/

CREATE OR REPLACE NONEDITIONABLE PROCEDURE Convert_Temperature (
    p_temperature            IN NUMBER,
    p_unit                   IN VARCHAR2,
    p_converted_temperature  OUT NUMBER
) AS
BEGIN
    -- Handle conversion logic based on the unit provided
    IF p_unit = 'C' THEN
        -- Convert from Celsius to Fahrenheit and round to 9 decimal places
        p_converted_temperature := ROUND((p_temperature * 9.0 / 5.0) + 32, 9);

    ELSIF p_unit = 'F' THEN
        -- Convert from Fahrenheit to Celsius and round to 9 decimal places
        p_converted_temperature := ROUND((p_temperature - 32) * 5.0 / 9.0, 9);

    ELSE
        -- Raise an application error for invalid units
        RAISE_APPLICATION_ERROR(-20001, 'Invalid unit. Use C for Celsius or F for Fahrenheit.');
    END IF;

EXCEPTION
    -- Handle invalid temperature input error (non-numeric or out of range)
    WHEN VALUE_ERROR THEN
        -- Raise an error with a more user-friendly message
        RAISE_APPLICATION_ERROR(-20002, 'Invalid temperature value. Please provide a valid numeric temperature.');

    -- Handle other known exceptions
    WHEN OTHERS THEN
        -- Capture any unexpected errors and raise an error with the error message and code
        RAISE_APPLICATION_ERROR(-20003, 'Unexpected error occurred: ' || SQLERRM);

END Convert_Temperature;
/***********************************************************************
 *  Class: TemperatureConverter
 *  Description:
 *      This program prompts the user to enter a temperature value and its unit
 *      (either Celsius or Fahrenheit), converts it to the opposite unit by
 *      calling a stored procedure in an Oracle database, and displays the result.
 *      The program interacts with the database using a `CallableStatement`
 *      to invoke the stored procedure that performs the conversion.
 *  Author: Neetu Mishra
 *  Version: 1.0
 *  Date: [10-Mar-2025]
 *  Change History:
 *  ----------------------------------------------------------------------------
 *  Version | Author       | Date       | Description
 *  ----------------------------------------------------------------------------
 *  1.0     | Neetu Mishra | [10-Mar-2025] | Initial version
 *  ----------------------------------------------------------------------------
 ***********************************************************************/

import java.sql.*;
import java.util.Scanner;
/**
 * TemperatureConverter class for handling temperature conversion.
 * It converts temperature between Celsius and Fahrenheit by calling a stored procedure
 * in an Oracle database.
 * The program validates user input, performs the conversion, and displays the result.
 */
public class TemperatureConverter {

    /**
     * Main method that drives the logic for temperature conversion.
     * It interacts with the user to take the temperature and unit,
     * then connects to the Oracle database and calls a stored procedure
     * to convert the temperature.
     */
    public static void main(String[] args) {
        // Create a scanner object to capture user input from the console
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the temperature value
        System.out.print("Enter temperature: ");
        double temperature = scanner.nextDouble();

        // Prompt the user to enter the unit of the temperature (Celsius or Fahrenheit)
        System.out.print("Enter unit (C/F): ");
        String unit = scanner.next().toUpperCase();  // Convert input to uppercase for consistency

        // Validate the user input for valid units (Celsius or Fahrenheit)
        if (!unit.equals("C") && !unit.equals("F")) {
            // If the unit is invalid, notify the user and exit
            System.out.println("Invalid unit. Use C for Celsius or F for Fahrenheit.");
            return;  // Exit the program if the unit is invalid
        }

        try {
            // Establish a connection to the Oracle database
            // Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:FREE", "sys", "test");
            // Connection string uses SYSDBA for internal connection
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1522/FREE?internal_logon=sysdba", "SYS", "test");

            // Prepare a callable statement to execute the stored procedure
            CallableStatement stmt = conn.prepareCall("{call Convert_Temperature(?, ?, ?)}");

            // Set the input parameters for the stored procedure (temperature and unit)
            stmt.setDouble(1, temperature);  // Set temperature value
            stmt.setString(2, unit);         // Set unit (C or F)

            // Register the output parameter for the converted temperature
            stmt.registerOutParameter(3, Types.NUMERIC);

            // Execute the stored procedure to perform the conversion
            stmt.execute();

            // Retrieve the converted temperature from the output parameter
            double convertedTemp = stmt.getDouble(3);

            // Output the converted temperature and corresponding unit
            System.out.println("Converted Temperature: " + convertedTemp + " " + (unit.equals("C") ? "F" : "C"));

            // Close the statement and the database connection
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            // Handle any SQL exceptions and display a message to the user
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

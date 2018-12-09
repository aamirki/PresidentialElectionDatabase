# Presidential Election Database Application
CS 3200 Final Project

This is a simple application to access the Presidential Election Database. It uses Swing to display everything to the user and allow the user to compare different metrics in reference to presidential elections and state demographics.

## Application Architecture

This application uses the Model-View-Controller (MVC) design pattern. The model is the DatabaseRetrieval class which fetches information from the Database. The controller is the PresElectionControllerInterface. The view is the PresElectionView.

## Starting the Application

If you have the database set up on your machine on the root connection (password set to "pass") you simply need to run the program with no arguments. If you do not have the database on your machine at all you need to run the two scripts named "Create Tables and Database.sql" and "Insert Into State Table.sql" located in the resources folder. Then run the application on the command line with the argument initDatabase. If the root password is not "pass" you must also type your password as the second argument. If you have the database initialized and populated on your machine but your root password is not set to "pass" you can enter only your password as an argument. If you clear the data from the database you may also use initDatabase as the first argument (and optionally your password as the second) to re-insert all the data. But you must have your state table filled out before this, so make sure to run "Insert Into State Table.sql" beforehand.

## How To Use

The top section of the window is the first jurisdiction section and the section below that is the second jurisdiction section. You may fill out the desired states, counties and years. Use the compare button to directly compare the first and second counties in the given years using the comparison metric (which can be chosen using the dropdown menu to the left of the compare button). To compare the entire state, use the "Compare Entire State" button. The uppermost label will display the comparison data, the label below it will display raw data about the first jurisdiction, and the label below that will display raw data about the second jurisdiction.

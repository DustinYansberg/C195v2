package com.example.c195v1;

import DAO.*;
import Model.*;
import helper.AppointmentAlert;
import helper.TimeHelper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;

import javafx.collections.ObservableList;

public class MainPageController implements Initializable{
    public Button quitButton;
    public TableView<Customer> customerTable;
    public TableColumn<Object, Object> colCustomerId;
    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colAddress;
    public TableColumn<Object, Object> colPostalCode;
    public TableColumn<Object, Object> colPhoneNumber;
    public TableColumn<Object, Object> colDivision;
    public TextField textCustomerPhone;
    public TextField textCustomerPostal;
    public TextField textCustomerAddress;
    public TextField textCustomerName;
    public TextField textCustomerID;
    public Button createCustomerButton;
    public Button createAppointmentButton;
    public Button confirmCustomerCreateButton;
    public Button confirmAppCreateButton;
    public Button deleteCustomerButton;
    public Button deleteAppointmentButton;
    public Button editCustomerButton;
    public Button editAppointmentButton;
    public ComboBox<String> comboCustomerCountry;
    public ComboBox<String> comboCustomerDivision;
    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Object> colUserId;
    public TableColumn<Appointment, Object> colEnd;
    public TableColumn<Appointment, Object> colCustId;
    public TableColumn<Appointment, Object> colStart;
    public TableColumn<Appointment, Object> colType;
    public TableColumn<Appointment, Object> colContact;
    public TableColumn<Appointment, String> colLocation;
    public TableColumn<Appointment, String> colDescription;
    public TableColumn<Appointment, String> colTitle;
    public TableColumn<Appointment, Integer> colAppointmentId;
    public ComboBox<String> comboAppContact;
    public TextField textAppType;
    public TextField textAppLocation;
    public TextField textAppDescription;
    public TextField textAppTitle;
    public ComboBox<String> comboAppUser;
    public ComboBox<String> comboAppCustomer;
    public TextField textAppId;
    public DatePicker dateStart;
    public ComboBox<String> hourStart;
    public ComboBox<String> minuteStart;
    public DatePicker dateEnd;
    public ComboBox<String> hourEnd;
    public ComboBox<String> minuteEnd;

    public Label alertCustomerLabel;
    public Label alertAppointmentLabel;


    public ComboBox<String> reportTypeCombo;
    public ComboBox<String> reportMonthCombo;
    public Label reportTypeTotal;
    public Label reportMonthTotal;
    public TableColumn<Object, Object> reportColAppId;
    public TableColumn<Object, Object> reportColTitle;
    public TableColumn<Object, Object> reportColType;
    public TableColumn<Object, Object> reportColDescription;
    public TableColumn<Object, Object> reportColStart;
    public TableColumn<Object, Object> reportColEnd;
    public TableColumn<Object, Object> reportColCustomerId;
    public Tab reportTab;
    public ComboBox<String> reportContactCombo;
    public TableView<Appointment> reportTable;
    public Label thirdReport;
    public ComboBox<String> filterAppointmentCombo;

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    
    public static Customer currCustomer;
    public static Appointment currAppointment;

    User activeUser = LoginController.activeUser.getActiveUser();



    /**
     * Runs at the start of the program
     * Builds customer table, country list, division list, contact list, user list, and the customer combo
    */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

//        build customer table
        try {
            buildCustomerTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        build appointment table and check if appointments are happening soon
        try {
            buildAppointmentTable();
            boolean fifteenMinuteCheck = false;
            for (Appointment appointment : allAppointments){
                if (appointment.getUserID() == activeUser.getUserId()){
                    if (TimeHelper.withinFifteenMinutes(appointment.getStart())){
//                        if appointment found, remember it through boolean, and display appropriate alert message
                        fifteenMinuteCheck = true;
                        if (Main.ENGLISH){
                            AppointmentAlert.setAlert("Welcome, this appoint will begin within fifteen minutes:");
                            appointmentAlertStageShow(appointment);
                        }else if (Main.FRENCH){
                            AppointmentAlert.setAlert("IN FRENCH Welcome, this appoint will begin within fifteen minutes:");
                            appointmentAlertStageShow(appointment);
                        }

                    }

                }
            }
//            If no appointment found, display appropriate message
            if (!fifteenMinuteCheck){
                if (Main.ENGLISH){
                    AppointmentAlert.setAlert("Welcome, you have no impending appointments");
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointment-alert-view.fxml")));
                    Stage stage = new Stage();
                    stage.setTitle("WELCOME USER");
                    stage.setScene(new Scene(parent));
                    stage.setAlwaysOnTop(true);
                    stage.show();
                }
                else if (Main.FRENCH){
                    AppointmentAlert.setAlert("IN FRENCH: Welcome, you have no impending appointments");
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointment-alert-view.fxml")));
                    Stage stage = new Stage();
                    stage.setTitle("WELCOME USER");
                    stage.setScene(new Scene(parent));
                    stage.setAlwaysOnTop(true);
                    stage.show();
                }

            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
//        build country list
        try {
            allCountries = getAllCountries();
            buildCountryCombo(allCountries);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        build division list
        try {
            allDivisions = getAllDivisions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        build contact list
        try{
            ContactDAO contactDAO = new ContactDAO();
            allContacts = contactDAO.get();
            buildContactCombo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        build user list
        try {
            UserDAO userDAO = new UserDAO();
            allUsers = userDAO.get();
            buildUserCombo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        build customer combo
        try {
            buildCustomerIdCombo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        build filter combo
        ObservableList<String> filterList = FXCollections.observableArrayList("All", "Month", "Week");
        filterAppointmentCombo.setItems(filterList);

//        Lambda Expressions
        createCustomerButton.setOnAction(e -> {
            disableCustomerFields(false);
            clearCustomerFields();
        });

        createAppointmentButton.setOnAction(e ->{
            disableAppointmentFields(false);
            clearAppointmentFields();
                });


    }

    private void appointmentAlertStageShow(Appointment appointment) throws IOException {
        AppointmentAlert.setAppointment(appointment);
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointment-alert-view.fxml")));
        Stage stage = new Stage();
        stage.setTitle("WELCOME USER");
        stage.setScene(new Scene(parent));
        stage.setAlwaysOnTop(true);
        stage.show();
    }


//---------------------------------------------------------------------------------------------------------------------


    /**
     * CUSTOMERS
     * This Section is for methods related to the customer table and text fields specifically
     */
    public void onCustomerTableClick(MouseEvent mouseEvent) throws SQLException, IOException {
        assignCurrCustomer();
    }

    public void onEditCustomerClick(ActionEvent actionEvent) throws SQLException, IOException {
        assignCurrCustomer();
        disableCustomerFields(false);
    }

    public void onConfirmCustomerClick(ActionEvent actionEvent) throws SQLException {
        CustomerDAO customerDAO = new CustomerDAO();
        int cid;
        String name;
        String address;
        String postal;
        String phone;
        int divId;

//        capture division id from name
        DivisionDAO divisionDAO = new DivisionDAO();
        Division division = divisionDAO.get(comboCustomerDivision.getValue());



        if (!Objects.equals(textCustomerID.getText(), "AUTO-GENERATED")){

            cid = Integer.parseInt(textCustomerID.getText());
            name = textCustomerName.getText();
            address = textCustomerAddress.getText();
            postal = textCustomerPostal.getText();
            phone = textCustomerPhone.getText();
            divId = division.getDivisionID();
            Customer customer = new Customer(cid, name, address, postal, phone, divId);
            customerDAO.update(customer);

        }else{

            name = textCustomerName.getText();
            address = textCustomerAddress.getText();
            postal = textCustomerPostal.getText();
            phone = textCustomerPhone.getText();
            divId = division.getDivisionID();
            Customer customer = new Customer(name, address, phone, postal, divId);
            customerDAO.insert(customer);


        }

        buildCustomerTable();
        disableCustomerFields(true);
        clearCustomerFields();



    }

    public void onDeleteCustomerClick(ActionEvent actionEvent) throws SQLException {
        boolean customerHasAppointments = false;

//        Get each customerID associated with the appointment table
        for(Appointment custId : appointmentTable.getItems()){
            int check = Integer.parseInt(String.valueOf(colCustId.getCellObservableValue(custId).getValue()));
            if (check == currCustomer.getCustomerID()){
                customerHasAppointments = true;
                break;
            }
//            idList.add(String.valueOf(colCustId.getCellObservableValue(custId).getValue()));
        }

        if (customerHasAppointments){
            alertCustomerLabel.setText("Customer's appointments must first be deleted.");
        }else{
            String name = currCustomer.getCustomerName();
            int cid = currCustomer.getCustomerID();
            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.delete(currCustomer.getCustomerID());
            alertCustomerLabel.setText("Customer named " + name + " with ID " + cid + " has been deleted.");

        }
        buildCustomerTable();
        disableCustomerFields(true);
        clearCustomerFields();

//          use to see if the list was populating properly.
//        for(String item : idList){
//            System.out.println(item);
//        }


    }

//---------------------------------------------------------------------------------------------------------------------

    /**
     * APPOINTMENTS
     * This Section is for methods related to the APPOINTMENT table and text fields specifically
     */
    public void onAppointmentTableClick(MouseEvent mouseEvent) throws IOException {
        assignCurrAppointment();
    }

    public void onEditAppointmentClick(ActionEvent actionEvent) throws IOException {
        assignCurrAppointment();
        disableAppointmentFields(false);
    }

    public boolean onConfirmAppointmentClick(ActionEvent actionEvent) throws SQLException {
        int appId;
        String title;
        String description;
        String location;
        String type;
        int customerID;
        int userID;
        int contactID;

        ZonedDateTime start = stringToLDT(dateStart.getValue(), hourStart.getValue(), minuteStart.getValue());
        ZonedDateTime end = stringToLDT(dateEnd.getValue(), hourEnd.getValue(), minuteEnd.getValue());

//        capture contact id from name
        ContactDAO contactDAO = new ContactDAO();
        Contact contact = contactDAO.get(comboAppContact.getValue());
//        capture customer id from name
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.get(comboAppCustomer.getValue());
//        capture user id from name
        UserDAO userDAO = new UserDAO();
        User user = userDAO.get(comboAppUser.getValue());

        AppointmentDAO appointmentDAO = new AppointmentDAO();
//        give value to the variables that will be used whether this is an update or new appointment
        title = textAppTitle.getText();
        description = textAppDescription.getText();
        location = textAppLocation.getText();
        type = textAppType.getText();

        customerID = customer.getCustomerID();
        userID = user.getUserId();
        contactID = contact.getContactID();

        if (TimeHelper.outsideBusinessHours(start) || TimeHelper.outsideBusinessHours(end)){
            alertAppointmentLabel.setText("Appointments must be scheduled Mon - Fri 8:00 AM - 10:00 PM EST");
            return false;
        }
        if (TimeHelper.isThereOverlappingAppointments(start, allAppointments, customer) ||
                TimeHelper.isThereOverlappingAppointments(end, allAppointments, customer)){
            alertAppointmentLabel.setText("This customer has an appointment at that time already!");
            return false;
        }

        if (!Objects.equals(textAppId.getText(), "AUTO-GENERATED")){
            appId = Integer.parseInt(textAppId.getText());
            Appointment appointment = new Appointment(appId, title, description, location, type,
                    start.toLocalDateTime(), end.toLocalDateTime(), customerID, userID, contactID);
            appointmentDAO.update(appointment);
        }else{
            Appointment appointment = new Appointment(title, description, location, type,
                    start.toLocalDateTime(), end.toLocalDateTime(), customerID, userID, contactID);
            appointmentDAO.insert(appointment);
        }




        buildAppointmentTable();
        disableAppointmentFields(true);
        clearAppointmentFields();
        return true;
    }

    public void onDeleteAppointmentClick(ActionEvent actionEvent) throws SQLException {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        alertAppointmentLabel.setText(
                        "Appointment number " + currAppointment.getAppointmentID() +
                        " with Type: " + currAppointment.getType() +
                        " has been deleted."
        );
        appointmentDAO.delete(currAppointment.getAppointmentID());
        buildAppointmentTable();
        disableAppointmentFields(true);
        clearAppointmentFields();

    }

    public void onContactSelect(ActionEvent actionEvent) {
    }


//    -----------------------------------------------------------------------------------------------------------------

    /**
     * COMBO BOXES
     * Methods focused on the combo boxes found in the
    */

    public void onCountrySelect(ActionEvent actionEvent) throws SQLException {
        String countryName = comboCustomerCountry.getValue();
        ObservableList<Country> allCountries = getAllCountries();
        int countryID = 0;
        for (Country country : allCountries){
            if (Objects.equals(country.getCountryName(), countryName)){
                countryID = country.getCountryID();
                break;
            }
        }
        buildDivisionCombo(countryID);
//        comboCustomerDivision.setDisable(false);
    }

    public void onDivisionSelect(ActionEvent actionEvent) {
    }

    public ObservableList<Country> getAllCountries() throws SQLException {
        CountryDAO countryDAO = new CountryDAO();
        return countryDAO.get();
    }

    public void buildCountryCombo(ObservableList<Country> allCountries){

//        comboCustomerCountry.setItems(null);

        for (Country allCountry : allCountries) {
            String name = allCountry.getCountryName();
            comboCustomerCountry.getItems().add(name);
        }
    }

    public ObservableList<Division> getAllDivisions() throws SQLException {
        DivisionDAO divisionDAO = new DivisionDAO();
        return divisionDAO.get();
    }

    public void buildDivisionCombo(int cid) throws SQLException {
        ObservableList<Division> allDivisions = getAllDivisions();
        ObservableList<String> filteredDivisionNames =  FXCollections.observableArrayList();

        allDivisions.removeIf(division -> cid != division.getCountryID());
        for (Division division : allDivisions){
            filteredDivisionNames.add(division.getDivisionName());
        }

        comboCustomerDivision.setItems(filteredDivisionNames);
    }

    public void buildEditCountryCombo() throws SQLException {
        CountryDAO countryDAO = new CountryDAO();
        DivisionDAO divisionDAO = new DivisionDAO();

        int currDivId = currCustomer.getDivisionID();
        Division division = divisionDAO.get(currDivId);
        int countryId = division.getCountryID();
        Country country = countryDAO.get(countryId);

        comboCustomerCountry.setValue(country.getCountryName());
        comboCustomerDivision.setValue(division.getDivisionName());


    }

    public void buildContactCombo() throws SQLException{
        for (Contact contact : allContacts){
            comboAppContact.getItems().add(contact.getName());
        }
    }

    public void buildCustomerIdCombo() throws SQLException{
        for (Customer customer : allCustomers){
            comboAppCustomer.getItems().add(customer.getCustomerName());
        }
    }

    public void buildUserCombo() throws SQLException {
        for (User user : allUsers){
            comboAppUser.getItems().add(user.getUserName());
        }
    }

//    -----------------------------------------------------------------------------------------------------------------
    /**
     * REPORT TAB
     * Methods Relating to the report tab
     * Reports will only be generated when the report tab is selected.
     * This helps to ensure data freshness.
     * */
    public void buildReportTypeCombo(){
        reportTypeCombo.getItems().clear();
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
        for( Appointment appointment : allAppointments){
            appointmentTypes.add(appointment.getType());
        }
        reportTypeCombo.setItems(appointmentTypes);

    }

    public void buildReportContactAppointments(ObservableList<Appointment> a){

        reportColAppId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        reportColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        reportColType.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportColStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        reportColEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        reportColCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        reportTable.setItems(a);

    }

    public void buildReportContactCombo(){
        reportContactCombo.getItems().clear();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contact contact : allContacts){
            reportContactCombo.getItems().add(contact.getName());
        }
    }

    public void buildReportMonthCombo(){
        ObservableList<String> allMonths = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December");
        reportMonthCombo.getItems().clear();
        reportMonthCombo.getItems().setAll(allMonths);
    }

    public void onReportSelected(Event event) {
        buildReportTypeCombo();
        buildReportContactCombo();
        buildReportMonthCombo();
        thirdReport.setText(String.valueOf(allCustomers.size()));
    }

    public void onReportTypeSelect(ActionEvent actionEvent) {
        int count = 0;
        for(Appointment appointment : allAppointments){
            if (Objects.equals(appointment.getType(), reportTypeCombo.getValue())){
                count++;
            }
        }
        reportTypeTotal.setText(String.valueOf(count));
    }

    public void onReportContactSelect(ActionEvent actionEvent) throws SQLException {
        ContactDAO contactDAO = new ContactDAO();
        Contact contact = contactDAO.get(reportContactCombo.getValue());

        ObservableList<Appointment> reportAppointments = FXCollections.observableArrayList();

        for(Appointment appointment: allAppointments){
            if (appointment.getContactID() == contact.getContactID()){
                reportAppointments.add(appointment);
            }
        }
        buildReportContactAppointments(reportAppointments);
    }

    public void onReportMonthSelect(ActionEvent actionEvent) {
        String month = reportMonthCombo.getValue();
        int count = 0;
        for(Appointment appointment : allAppointments){
            if (Objects.equals(month, appointment.getStart().getMonth().getDisplayName(TextStyle.FULL, Locale.US))){
                count++;
            }
        }
        reportMonthTotal.setText(String.valueOf(count));
    }


//    -----------------------------------------------------------------------------------------------------------------

    /**
     * When user selects an appointment filter, filter out the tableview according to the filterAppointmentCombo selection
     */
    public void onFilterSelect(ActionEvent actionEvent) {
        String filter = filterAppointmentCombo.getValue();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        if (Objects.equals(filter, "Month")){
            ZonedDateTime systemZdt = ZonedDateTime.now();
            Month month = systemZdt.getMonth();
            String m = month.getDisplayName(TextStyle.FULL, Locale.US);

            for(Appointment appointment: allAppointments){
                Month aMonth = appointment.getStart().getMonth();
                String aM = aMonth.getDisplayName(TextStyle.FULL,Locale.US);
                if (aM.equals(m)){
                    filteredAppointments.add(appointment);
                }
            }
            appointmentTable.setItems(filteredAppointments);
        }else if (Objects.equals(filter, "Week")){
            ZonedDateTime systemZdt = ZonedDateTime.now();
            int iWeekSystem = systemZdt.get(WeekFields.of(Locale.US).weekOfMonth());

            for(Appointment appointment : allAppointments){
                LocalDateTime aLdt = appointment.getStart();
                int iWeekAppointment = aLdt.get(WeekFields.of(Locale.US).weekOfMonth());
                if(iWeekAppointment == iWeekSystem){
                    filteredAppointments.add(appointment);
                }
            }
            appointmentTable.setItems(filteredAppointments);
        }else{
            appointmentTable.setItems(allAppointments);
        }
    }


    //    -----------------------------------------------------------------------------------------------------------------

    /**
     * QUIT BUTTON
     * close program when quit is selected
    */
    public void onQuitButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }





    /*
      HELPER CLASSES for this page
     */


    /**
     * simple method to assign the currCustomer variable.
     */
    private void assignCurrCustomer() throws SQLException, IOException {
        currCustomer = null;
        currCustomer = customerTable.getSelectionModel().getSelectedItem();
        alertCustomerLabel.setText("");
        if (currCustomer != null){
            textCustomerID.setText(String.valueOf(currCustomer.getCustomerID()));
            textCustomerName.setText(currCustomer.getCustomerName());
            textCustomerPhone.setText(currCustomer.getPhone());
            textCustomerPostal.setText(currCustomer.getPostalCode());
            textCustomerAddress.setText(currCustomer.getAddress());
            buildEditCountryCombo();
        } else if (Main.ENGLISH){
            Main.errorMessage.setErrorMessage("First select a customer.");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("error-view.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(parent));
            stage.show();
        } else if (Main.FRENCH) {
            Main.errorMessage.setErrorMessage("Sélectionnez d'abord un client.");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("error-view.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    /**
     * simple method to assign the currAppointment variable.
     */
    private void assignCurrAppointment() throws IOException {
        currAppointment = null;
        currAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (currAppointment != null){
            textAppId.setText(String.valueOf(currAppointment.getAppointmentID()));
            textAppType.setText(currAppointment.getType());
            textAppLocation.setText(currAppointment.getLocation());
            textAppDescription.setText(currAppointment.getDescription());
            textAppTitle.setText(currAppointment.getTitle());

//        Populate appointment start time and date
            dateStart.setValue(currAppointment.getStart().toLocalDate());
            hourStart.setValue(String.valueOf(currAppointment.getStart().getHour()));
            minuteStart.setValue(String.valueOf(currAppointment.getStart().getMinute()));

//        Populate appoint end time and date
            dateEnd.setValue(currAppointment.getEnd().toLocalDate());
            hourEnd.setValue(String.valueOf(currAppointment.getEnd().getHour()));
            minuteEnd.setValue(String.valueOf(currAppointment.getEnd().getMinute()));
        } else if (Main.ENGLISH) {
            Main.errorMessage.setErrorMessage("First select an appointment.");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("error-view.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(parent));
            stage.show();
        } else if (Main.FRENCH) {
            Main.errorMessage.setErrorMessage("\n" +
                    "Choisissez d'abord un rendez-vous.");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("error-view.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Error");
            stage.setScene(new Scene(parent));
            stage.show();
        }


    }

    /**
     * removes all the information entered into the customer fields
     */
    public void clearCustomerFields(){
        textCustomerID.setText("AUTO-GENERATED");
        textCustomerName.clear();
        textCustomerAddress.clear();
        textCustomerPhone.clear();
        textCustomerPostal.clear();

    }

    /**
     * Enable or Disable all the customer fields
     */
    private void disableCustomerFields(Boolean b) {
        textCustomerName.setDisable(b);
        textCustomerAddress.setDisable(b);
        textCustomerPhone.setDisable(b);
        textCustomerPostal.setDisable(b);
        confirmCustomerCreateButton.setDisable(b);
        comboCustomerDivision.setDisable(b);
        comboCustomerCountry.setDisable(b);
    }

    /**
     * Enable or Disable all the Appointment fields
     */
    public void disableAppointmentFields(Boolean b){

        textAppType.setDisable(b);
        textAppLocation.setDisable(b);
        textAppDescription.setDisable(b);
        textAppTitle.setDisable(b);
        confirmAppCreateButton.setDisable(b);

        comboAppUser.setDisable(b);
        comboAppCustomer.setDisable(b);
        comboAppContact.setDisable(b);

        dateEnd.setDisable(b);
        hourEnd.setDisable(b);
        minuteEnd.setDisable(b);

        dateStart.setDisable(b);
        hourStart.setDisable(b);
        minuteStart.setDisable(b);
//
////        Build customer combo box
//        for (Customer customer : allCustomers){
//            comboAppCustomer.getItems().add(customer.getCustomerName());
//        }
////        build user combo box
//        for (User user : allUsers){
//            comboAppUser.getItems().add(user.getUserName());
//        }


    }

    public void clearAppointmentFields(){
        textAppId.setText("AUTO-GENERATED");
        textAppType.clear();
        textAppLocation.clear();
        textAppDescription.clear();
        textAppTitle.clear();
        comboAppUser.setValue("Select a User");
        comboAppCustomer.setValue("Select a Customer");
        comboAppContact.setValue("Select a Contact");




    }

    public void buildCustomerTable() throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO();
        allCustomers = customerDAO.get();

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        customerTable.setItems(allCustomers);
    }

    public void buildAppointmentTable() throws SQLException {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        allAppointments = appointmentDAO.get();
        appointmentTable.setItems(allAppointments);

        colAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
//        colAppointmentId.setCellValueFactory(a -> a.);
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//        colTitle.setCellValueFactory(a -> a.getValue().getType());
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));


    }

    private ZonedDateTime stringToLDT(LocalDate d, Object h, Object m) {
        String ldtString = (d.toString() + " " + h + ":" + m);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(ldtString,formatter);

        return ldt.atZone(ZoneId.of(Main.systemZoneId.getId()));
    }

    private String ldtToString(Timestamp timestamp){
        return timestamp.toString();
    }
}

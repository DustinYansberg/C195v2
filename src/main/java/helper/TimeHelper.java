package helper;

import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import com.example.c195v1.Main;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeHelper {

    public static Date dateFromUTC(Date date){
        return new Date(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(new Date().getTime()));
    }

    public static Date dateToUTC(Date date){
        return new Date(date.getTime() - Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
    }

    /**
     * take in a LocalDateTime object from an appointment, and see if that appointment is within fifteen minutes of
     * right now. Return Bool based on findings
     * @param a - Appointment LDT object. Check this against current time.
     * @return true if appointment is within 15 minutes
     */
    public static Boolean withinFifteenMinutes(LocalDateTime a){
        return a.isBefore(LocalDateTime.now().plusMinutes(15)) && a.isAfter(LocalDateTime.now());
    }

    public static ZonedDateTime systemDateTime(LocalDateTime ldt){
//        return the inputted time as the ZonedDateTime of the system's ZoneId
        return ldt.atZone(ZoneId.of(ZoneId.systemDefault().getId()));
    }

    /**
     * check to see if the time that is passed in is outside business hours of Mon - Friday, 08:00 to 20:00 EST
     * @param systemZdt used as the time that is taken in from the appointment scheduler
     * @return true if time is outside business hours
     */
    public static boolean outsideBusinessHours(ZonedDateTime systemZdt){
        ZonedDateTime estZdt = systemZdt.withZoneSameInstant(ZoneId.of("UTC-05:00"));
        ZonedDateTime openingHour = estZdt.withHour(8).withMinute(0);
        ZonedDateTime closingHour = estZdt.withHour(20).withMinute(0);
        String dayOfWeek = estZdt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        return "Saturday".equals(dayOfWeek) || "Sunday".equals(dayOfWeek) ||
                estZdt.isAfter(closingHour) || estZdt.isBefore(openingHour);
    }

    /**
     * Check to see if a new appointment overlaps any existing appointments
     * @param systemZdt the start or end time of the appointment, in the systems ZonedDateTime
     * @param allAppointments list of all current appointments
     * @param customer The customer that this appointment is being made for
     * @return true if the new appointment overlaps with any existing appointments
     * @throws SQLException because I was told by the system this was needed
     */
    public static boolean isThereOverlappingAppointments(ZonedDateTime systemZdt,
                                                         ObservableList<Appointment> allAppointments,
                                                         Customer customer) throws SQLException {
        CustomerDAO customerDAO = new CustomerDAO();
        for (Appointment appointment : allAppointments){
            Customer appCustomer = customerDAO.get(appointment.getCustomerID());
            if (customer.getCustomerID() == appCustomer.getCustomerID()){
                ZonedDateTime start = appointment.getStart().atZone(ZoneId.of(Main.systemZoneId.getId()));
                ZonedDateTime end = appointment.getEnd().atZone(ZoneId.of(Main.systemZoneId.getId()));
                if (systemZdt.isBefore(end) && systemZdt.isAfter(start)){
                    return true;
                }
            }
        }
        return false;
    }
}

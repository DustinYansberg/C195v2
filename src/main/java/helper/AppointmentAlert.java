package helper;

import Model.Appointment;

public class AppointmentAlert {

    public static String appointmentAlert;
    public static Appointment appointment;

    public AppointmentAlert(String m, Appointment a){
        appointmentAlert = m;
        appointment = a;
    }

    public static Appointment getAppointment(){
        return appointment;
    }

    public static void setAlert(String s) {
        appointmentAlert = s;
    }

    public static void setAppointment(Appointment a) {
        appointment = a;
    }

    public static String getAlert() {
        return appointmentAlert;
    }
}

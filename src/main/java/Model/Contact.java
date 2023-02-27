package Model;

public class Contact {


//    The following commented-out line of code is from some unrelated guide
//    private static DAO<Contact> ContactDao;
    private int contactID;
    private String name;
    private String email;

    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactID=" + contactID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

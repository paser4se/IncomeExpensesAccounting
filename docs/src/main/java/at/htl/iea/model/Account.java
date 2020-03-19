package at.htl.iea.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Account.getAccountByUsername", query = "select a from Account a where a.username = ?1"),
        @NamedQuery(name = "Account.getAccountByEmail", query = "select a from Account a where a.email = ?1")
})
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;
    private String fullName;
    private String prefix;
    private String address;
    private String notes;

    // region Constructor
    public Account(){}
    public Account(String username, String password, String fullName, String email){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }
    // endregion

    // region Getter & Setter
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // endregion

}

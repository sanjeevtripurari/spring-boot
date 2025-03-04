package net.javaguides.postgresql.model;

import jakarta.persistence.*;

@Entity
@Table(name="employees")

public class Employee {
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;

    public Employee(){}

    public Employee(String firstName, String lastName, String emailId){
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailId=emailId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }

    @Column(name="first_name", nullable = false)
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="last_name",nullable = false)
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName= lastName;
    }

    @Column(name="email_address", nullable = false)
    public String getEmailId(){
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString(){
        String returnSt=String.format("Employee [id=%sd, firstName=%s, lastName=%s, emailid=%s", id, firstName, lastName, emailId);
        return (returnSt);
    }
}

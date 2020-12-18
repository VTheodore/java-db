package entities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.util.Date;

@Entity(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "age1")
    private int age1;

    @Column(name = "age2")
    private int age2;

    public User() {

    }

    public User(String username, String password, int age, Date registrationDate) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}

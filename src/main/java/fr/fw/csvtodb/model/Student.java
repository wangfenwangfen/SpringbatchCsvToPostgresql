package fr.fw.csvtodb.model;


import javax.persistence.*;

//Hibernate will make a table from this class
@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "firstname", length = 30, nullable = false)
    private String firstName;

    @Column(name = "lastname", length = 30, nullable = false)
    private String lastName;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    //nullable
    @Column(name = "age", length = 30)
    private int age;

    public Student() {
    }

    private Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Student transformer(){
        return new Student(this.firstName.toUpperCase(), this.lastName.toUpperCase(), this.email.toLowerCase(),this.age);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

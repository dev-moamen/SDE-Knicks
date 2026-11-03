package schoolProject.Models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity 
public class Customer {
    
    @Id 
    @GeneratedValue 
    long id;
    String name;
    String email;
    @Enumerated(EnumType.STRING)
    GenderType gender;
    int age;
    
    public enum GenderType {
        Male, Female, Gay
    }

    public Customer() {}

    public Customer(String name, String email, GenderType gender, int age) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
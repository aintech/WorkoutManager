package ru.aintech.workoutmanager.persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Entity(name = "_user")
public class User implements Serializable {
    
    @Id
    @Column(name = "_id")
    private int id;
    
    @NotNull
    @Size(min = 5, max = 16, message = "{username.size}")
    @Column(name = "_username")
    private String username;
    
    @NotNull
    @Size(min = 5, max = 24, message = "{password.size}")
    @Column(name = "_password")
    private String password;

    public User () {}
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
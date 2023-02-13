package project.com.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;


public class Book {

    public Client owner;
    private int id;
    @NotBlank(message = "title shouldn't be empty")
    @Pattern(regexp = "[A-Z]\\w+ [a-zA-Z]\\w*", message = "title should start with uppercase")
    private String title;
    @NotBlank(message = "author shouldn't be empty")

    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "author should start with uppercase")
    private String author;
    @Min(value = 900, message = "year must in range of 900-2022")
    @Max(value = 2022, message = " year must in range of 900-2022")
    private int year;
    private Date takenAt;


    private boolean overdue;

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }


    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

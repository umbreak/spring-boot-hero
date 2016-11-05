package model.fantasy;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
/**
 * Created by didac on 23.08.16.
 */
public class Hero {
    private String name;
    private String pseudonym;
    private Publisher publisher;
    private List<String> skills;
    private List<String> allies;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date firstAppearance;

    public Hero() {
    }

    public Hero(String name, String pseudonym) {
        this.name = name;
        this.pseudonym = pseudonym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getAllies() {
        return allies;
    }

    public void setAllies(List<String> allies) {
        this.allies = allies;
    }

    public Date getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(Date firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public boolean hasRequiredFields(){
        return (name != null && !name.isEmpty() && pseudonym != null && !pseudonym.isEmpty());
    }
}

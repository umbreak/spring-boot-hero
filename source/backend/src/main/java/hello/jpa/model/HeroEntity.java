package hello.jpa.model;

import model.fantasy.Hero;
import model.fantasy.Publisher;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(uniqueConstraints= @UniqueConstraint(columnNames = {"name", "pseudonym"}))
public class HeroEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    private String pseudonym;

    private Publisher publisher;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
    private List<HeroSkills> skills;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
    private List<HeroAlly> allies;

    private Date firstAppearance;

    public HeroEntity() {
    }

    public HeroEntity(Hero hero) {
        name = hero.getName();
        pseudonym = hero.getPseudonym();
        publisher = hero.getPublisher();
        allies = toAlliesList(hero.getAllies());
        skills = toSkillsList(hero.getSkills());
        firstAppearance = hero.getFirstAppearance();

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

    public Date getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(Date firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public List<HeroSkills> getSkills() {
        return skills;
    }

    public void setSkills(List<HeroSkills> skills) {
        this.skills = skills;
    }

    public List<HeroAlly> getAllies() {
        return allies;
    }

    public void setAllies(List<HeroAlly> allies) {
        this.allies = allies;
    }

    public Hero toAPIResponse(){
        Hero hero = new Hero();
        hero.setName(name);
        hero.setAllies(toStringList(allies));
        hero.setFirstAppearance(firstAppearance);
        hero.setPseudonym(pseudonym);
        hero.setPublisher(publisher);
        hero.setSkills(toStringList(skills));
        return hero;
    }

    private <T> List<String> toStringList(List<T> list){
        if(list == null) return null;
        return list.stream().map(ally -> ally.toString()).collect(Collectors.toList());
    }

    private List<HeroAlly> toAlliesList(List<String> list){
        if(list == null) return null;
        return list.stream().map(ally -> new HeroAlly(ally, this)).collect(Collectors.toList());
    }

    private List<HeroSkills> toSkillsList(List<String> list){
        if(list == null) return null;
        return list.stream().map(skill -> new HeroSkills(skill, this)).collect(Collectors.toList());
    }
}

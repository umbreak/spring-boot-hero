package hello.jpa.model;

import model.fantasy.Hero;

import javax.persistence.*;

@Entity
public class HeroSkills {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="hero_id")
    private HeroEntity owner;

    private String skill;

    public HeroSkills() {
    }
    public HeroSkills(String skill, HeroEntity hero) {
        this.skill = skill;
        this.owner = hero;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HeroEntity getOwner() {
        return owner;
    }

    public void setOwner(HeroEntity owner) {
        this.owner = owner;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return skill;
    }
}

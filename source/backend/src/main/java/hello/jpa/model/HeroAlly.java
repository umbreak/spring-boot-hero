package hello.jpa.model;

import model.fantasy.Hero;

import javax.persistence.*;

@Entity
public class HeroAlly {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="hero_id")
    private HeroEntity owner;

    private String heroName;

    public HeroAlly() {
    }

    public HeroAlly(String name, HeroEntity hero) {
        this.heroName = name;
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

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    @Override
    public String toString() {
        return heroName;
    }
}

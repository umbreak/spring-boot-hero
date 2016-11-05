package model.fantasy;

import java.util.List;

/**
 * Created by didac on 23.08.16.
 */
public class Heroes {

    private List<Hero> heroes;
    private Pagination page;

    public Heroes() {
    }

    public Heroes(List<Hero> heroes, Pagination page) {
        this.heroes = heroes;
        this.page = page;
    }

    public Heroes(Pagination page) {
        this.page = page;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public Pagination getPage() {
        return page;
    }

    public void setPage(Pagination page) {
        this.page = page;
    }
}

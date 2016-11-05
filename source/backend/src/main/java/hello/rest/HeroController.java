package hello.rest;

import com.google.common.base.Strings;
import hello.jpa.dao.HeroDAO;
import hello.jpa.model.HeroEntity;
import hello.rest.exceptions.HeroException;
import hello.rest.exceptions.HeroNotFoundException;
import hello.utils.PaginationUtils;
import model.fantasy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hero")
public class HeroController {

    private final HeroDAO dao;

    @Autowired
    public HeroController(HeroDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Heroes listAll(@RequestParam(defaultValue = "10", required = false) int size, @RequestParam(defaultValue = "0", required = false) int offset) {

        Page<HeroEntity> heroesPage = dao.findAll(PaginationUtils.generatePageable(size, offset));

        Pagination paginationResponse = PaginationUtils.toPagination(heroesPage, offset, size);

        return new Heroes(toAPIResponse(heroesPage.getContent()), paginationResponse);
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public Hero getOne(@PathVariable("name") String name) {
        Optional<HeroEntity> heroEntity = dao.findByName(name);
        if(!heroEntity.isPresent()) throw new HeroNotFoundException();
        return heroEntity.get().toAPIResponse();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.DELETE)
    public Hero deleteOne(@PathVariable("name") String name) {
        Optional<HeroEntity> heroEntityOption = dao.findByName(name);
        if(!heroEntityOption.isPresent()) throw new HeroNotFoundException();

        HeroEntity heroEntity = heroEntityOption.get();
        dao.delete(heroEntity);
        return heroEntity.toAPIResponse();
    }

    @RequestMapping(method = RequestMethod.POST)
    public SuccessResponse addOne(@RequestBody Hero hero) {
        checkHeroBeforeStore(hero);

        HeroEntity heroEntity = new HeroEntity(hero);
        dao.save(heroEntity);

        return new SuccessResponse(SuccessResponse.Code.HERO_CREATED);
    }

    private void checkHeroBeforeStore(Hero hero){
        if(!hero.hasRequiredFields()) throw new HeroException(ErrorResponse.Error.PRECONDITIONS_MISSING);

        if(dao.countByNameAndPseudonym(hero.getName(), hero.getPseudonym()) > 0)
            throw new HeroException(ErrorResponse.Error.HERO_ALREADY_EXISTS);
    }



    private List<Hero> toAPIResponse(List<HeroEntity> heroes){
        if(heroes == null || heroes.isEmpty()) return null;
        return heroes.stream().map(hero -> hero.toAPIResponse()).collect(Collectors.toList());
    }

}

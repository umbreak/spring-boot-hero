package hello;

import model.fantasy.ErrorResponse;
import model.fantasy.Hero;
import model.fantasy.Publisher;
import model.fantasy.SuccessResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by didac on 24.08.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class HeroControllerTest {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testResources() throws Exception {
        //get all resource
        getAll(0);
        //get hero Hero2
        mockMvc.perform(get("/hero/Hero2")
                .contentType(contentType).with(user("user1").password("secret1")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(ErrorResponse.Error.HERO_NOT_FOUND.toString()));

        //Add a hero
        Hero hero = generateHero();
        addOne(hero);
        //Get all
        getAll(1);
        //Get the added hero
        getOne(hero);

        //try to add the same hero
        addOneDuplicated(hero);

        Hero hero2 = generateHero();
        addOne(hero2);
        //Get all
        getAll(2);
        //Get the added hero
        getOne(hero2);

    }

    @Test
    public void addAndRemove() throws Exception {
        Hero hero = generateHero();
        addOne(hero);
        getOne(hero);
        deleteOne(hero);
        mockMvc.perform(get("/hero/" + hero.getName())
                .contentType(contentType).with(user("user1").password("secret1")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(ErrorResponse.Error.HERO_NOT_FOUND.toString()));

    }

    private void getAll(int expected) throws Exception {
        mockMvc.perform(get("/hero")
                .with(user("user1").password("secret1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.currentPage").value(0))
                .andExpect(jsonPath("$.page.totalElements").value(expected));
    }

    private void deleteOne(Hero hero) throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/hero/" + hero.getName())
                .with(user("user1").password("secret1")))
                .andExpect(status().isOk());
        checkHeroMatchesReturned(hero, resultActions);
    }

    private void checkHeroMatchesReturned(Hero hero, ResultActions returned) throws Exception {
        String formatedDate = this.format.format(hero.getFirstAppearance());
        returned.andExpect(jsonPath("$.name").value(hero.getName()))
                .andExpect(jsonPath("$.pseudonym").value(hero.getPseudonym()))
                .andExpect(jsonPath("$.publisher").value(hero.getPublisher().toString()))
                .andExpect(jsonPath("$.firstAppearance").value(formatedDate))
                .andExpect(jsonPath("$.skills", hasSize(hero.getSkills().size())))
                .andExpect(jsonPath("$.allies", hasSize(hero.getAllies().size())));
    }

    private void addOne(Hero hero) throws Exception {
        mockMvc.perform(post("/hero")
                .contentType(contentType)
                .content(json(hero)).with(user("user1").password("secret1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(SuccessResponse.Code.HERO_CREATED.toString()));
    }

    private void addOneDuplicated(Hero hero) throws Exception {
        mockMvc.perform(post("/hero")
                .contentType(contentType)
                .content(json(hero)).with(user("user1").password("secret1")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(ErrorResponse.Error.HERO_ALREADY_EXISTS.toString()));
    }

    private Hero generateHero(){
        Random random = new Random();
        Hero hero = new Hero("Hero " + random.nextInt(), UUID.randomUUID().toString());
        Publisher publisher = random.nextBoolean() ? Publisher.DC : Publisher.MARVEL;
        hero.setPublisher(publisher);
        hero.setFirstAppearance(new Date());
        hero.setSkills(Arrays.asList("Extra power", "Invisibility", "Extra resistance"));
        hero.setAllies(Arrays.asList("Superman", "Spiderman"));
        return hero;
    }

    private void getOne(Hero hero ) throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/hero/" + hero.getName())
                .with(user("user1").password("secret1")))
                .andExpect(status().isOk());
        checkHeroMatchesReturned(hero, resultActions);
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}

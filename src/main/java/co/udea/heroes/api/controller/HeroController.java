package co.udea.heroes.api.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.udea.heroes.api.domain.Hero;
import co.udea.heroes.api.exception.DataNotFoundException;
import co.udea.heroes.api.service.HeroService;
import co.udea.heroes.api.util.Messages;

@RestController
@RequestMapping("/tourofheroes")
public class HeroController {
	
	private static Logger log = LoggerFactory.getLogger(HeroController.class);
	
	@Autowired
    private Messages messages;	

	@Autowired
	@Qualifier("HeroServiceImpl")
	private HeroService heroService;

	
	@RequestMapping("listar")
	public List<Hero> getHeros(){
		return heroService.getHeroes();		
	}
	
	@RequestMapping("consultar")
	public Hero getHero(int id) throws DataNotFoundException{
		log.debug("Entro a consultar");
		Optional<Hero> hero = heroService.getHero(id);
		if(!hero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		return hero.get();
	}
	
	@RequestMapping("buscar")
	public Hero searchHeroes(String term) throws DataNotFoundException{
		log.debug("Entro a buscar");
		Optional<Hero> hero = heroService.searchHeroes(term);
		if(!hero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		return hero.get();
	}
	

	@RequestMapping(value="actualizar", method=RequestMethod.PUT)
	public Hero updateHero(@RequestBody Hero hero) throws DataNotFoundException{
		log.debug("Entro a actualizar");
		int oldId = hero.getId();
		Optional<Hero> oldHero = heroService.getHero(oldId);
		if(!oldHero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
	
		return heroService.updateHero(hero);

	}
	
	
	@RequestMapping(value="crear", method=RequestMethod.POST)
	public Hero addHero(@RequestBody Hero hero) throws DataNotFoundException{
		log.debug("Entro a crear");
		if(hero == null){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		return heroService.addHero(hero);		
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="borrar")
	public String deleteHero(@RequestBody Hero hero){
		log.debug("Entro a borrar");
		int oldId = hero.getId();
		Optional<Hero> oldHero = heroService.getHero(oldId);
		if(!oldHero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
	
		heroService.deleteHero(hero);
		return null;

	}
	
	
}

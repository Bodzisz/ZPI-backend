package zpi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import zpi.attraction.dao.AttractionDAO;
import zpi.attraction.entity.Attraction;

@SpringBootApplication
public class ZpiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZpiBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AttractionDAO attractionDAO){
        return runner ->{
//            createAttraction(attractionDAO);
            readAttraction(attractionDAO);
        };
    }

    private void readAttraction(AttractionDAO attractionDAO) {
        Attraction tempAttraction = new Attraction(23,"wrocławski","Muzeum Narodowe we Wrocławiu","Muzeum","Muzeum Narodowe we Wrocławiu to bogaty zbiór sztuki od średniowiecza do czasów współczesnych. Do obejrzenia są dzieła Matejki czy Lucasa Cranacha starszego.","Wrocław","50-153", 51.111012808940956F, 17.04791652758684F);
        attractionDAO.addAttraction(tempAttraction);
        int theId = tempAttraction.getId();
        System.out.println("Saved attraction. Generated id: "+ theId);
        System.out.println("Retriving attraction with id: "+theId);
        Attraction myAttraction = attractionDAO.getById(theId);
        System.out.println("Found the attraction: "+ myAttraction);
    }

    private void createAttraction(AttractionDAO attractionDAO) {
        Attraction tempAttraction = new Attraction(19,"świdnicki", "Muzeum Dawnego Kupiectwa","Muzeum","Muzeum Dawnego Kupiectwa zlokalizowane jest w zabytkowym ratuszu w Świdnicy. Obiekt rozpoczął swoją działalność już w 1967 roku i miał za zadanie gromadzić i przechowywać zabytki związane z historią kupiectwa śląskiego, systemów pomiarowych tu stosowanych oraz historią Świdnicy.","Świdnica","58-100", 50.84301751613223F, 16.486729282500313F);
        System.out.println("Saving the attraction ...");
        attractionDAO.addAttraction(tempAttraction);
        System.out.println("Saved attraction with id: "+tempAttraction.getId());

    }

}

package zpi.attraction.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import zpi.attraction.entity.Attraction;

import java.util.List;

@Repository
@AllArgsConstructor
public class AttractionDAOImpl implements AttractionDAO{

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void addAttraction(Attraction theAttraction) {
        entityManager.persist(theAttraction);
    }

    @Override
    public Attraction getById(Integer id) {
        return entityManager.find(Attraction.class, id);
    }

    @Override
    public List<Attraction> getAllAttractions() {
        return entityManager.createQuery("SELECT a FROM Attraction a", Attraction.class).getResultList();
    }
}

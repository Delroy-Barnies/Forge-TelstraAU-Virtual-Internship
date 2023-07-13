package au.com.telstra.simcardactivator.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import au.com.telstra.simcardactivator.entities.SimCard;

@Repository
public interface SimCardRepository extends CrudRepository<SimCard, Long> {

}

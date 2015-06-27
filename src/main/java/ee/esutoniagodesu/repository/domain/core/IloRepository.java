package ee.esutoniagodesu.repository.domain.core;

import ee.esutoniagodesu.domain.core.table.Ilo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IloRepository extends JpaRepository<Ilo, Integer> {


    //@Procedure
    //List<Ilo> findRolesViaProcedure();

}

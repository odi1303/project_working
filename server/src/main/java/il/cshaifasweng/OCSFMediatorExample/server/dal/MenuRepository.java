package il.cshaifasweng.OCSFMediatorExample.server.dal;

import org.springframework.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Transactional
// @Repository
public interface MenuRepository extends CrudRepository<MenuItem, Long>
{
}

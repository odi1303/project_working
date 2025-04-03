package org.example.finalproject.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.finalproject.dal.models.MenuItem;

@Repository
public interface MenuRepository extends CrudRepository<MenuItem, Long>
{
}

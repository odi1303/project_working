package org.example.finalproject.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.finalproject.dal.models.complains.Complain;

@Repository
public interface ComplainsRepository extends CrudRepository<Complain, Long>
{
}

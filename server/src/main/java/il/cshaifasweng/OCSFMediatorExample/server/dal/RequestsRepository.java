package org.example.finalproject.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.finalproject.dal.models.requests.Request;

@Repository
public interface RequestsRepository extends CrudRepository<Request, Long>
{
}

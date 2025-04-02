package org.example.finalproject.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.finalproject.dal.models.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer>
{
}
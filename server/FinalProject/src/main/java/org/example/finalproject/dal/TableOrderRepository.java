package org.example.finalproject.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.finalproject.dal.models.TableOrder;

@Repository
public interface TableOrderRepository extends CrudRepository<TableOrder, Long>
{
}

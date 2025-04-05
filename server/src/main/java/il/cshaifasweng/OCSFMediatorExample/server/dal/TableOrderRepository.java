package il.cshaifasweng.OCSFMediatorExample.server.dal;

import org.springframework.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.TableOrder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

@Transactional
//@Repository
public interface TableOrderRepository extends CrudRepository<TableOrder, Long>
{
}

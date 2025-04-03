package org.example.finalproject.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.finalproject.dal.models.Delivery;

import javax.swing.*;

@Repository
public interface DeliveriesRepository extends CrudRepository<Delivery, Long>
{
}
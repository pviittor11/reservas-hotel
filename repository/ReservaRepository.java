package com.example.demohotelreservasapi.repository;

import com.example.demohotelreservasapi.entity.Reserva;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class ReservaRepository {

    @PersistenceContext
    private EntityManager entityManager = null;

    public List<Reserva> getByHotelAndData(LocalDate data, int hotelId) {
        String query = "FROM Reserva r WHERE :dataParametro BETWEEN r.checkIn AND r.checkOut AND r.status <> 'finalizada' AND r.hotelId = :hotelId";

        TypedQuery<Reserva> typedQuery = entityManager.createQuery(query, Reserva.class);
        typedQuery.setParameter("dataParametro", data);
        typedQuery.setParameter("hotelId", hotelId);

        return typedQuery.getResultList();

    }

    public Reserva save(Reserva reserva) {
        if (entityManager != null) {
            entityManager.persist(reserva);

            return reserva;
        }
        return reserva;
    }
}
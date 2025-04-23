package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Apartment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Integer> {

    Apartment findApartmentById(Integer id);
    @Modifying
    @Query("UPDATE Apartment a SET a.owner = null WHERE a.owner.id = :ownerId")
    void detachAllApartmentsFromOwner(@Param("ownerId") Integer ownerId);



    @Transactional
    @Modifying
    @Query("DELETE FROM Apartment a WHERE a.owner.id = :ownerId")
    void deleteByOwnerId(@Param("ownerId") Integer ownerId);
}

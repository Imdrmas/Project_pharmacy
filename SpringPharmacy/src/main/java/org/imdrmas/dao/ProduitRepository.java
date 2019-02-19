package org.imdrmas.dao;

import org.imdrmas.entities.Produits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitRepository extends JpaRepository<Produits, Long> {
	@Query("select p from Produits p where p.designation like :x")
	public Page<Produits> chercher(@Param("x")String mc, Pageable pageable);
}

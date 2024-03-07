package atos.tiposcardapios.repository;

import atos.tiposcardapios.entity.TiposCardapioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TiposCardapioRepository extends JpaRepository<TiposCardapioEntity, Integer> {

    TiposCardapioEntity findByNome(String nome);

    Page<TiposCardapioEntity> findByNomeContaining(String nome, Pageable pageable);

}

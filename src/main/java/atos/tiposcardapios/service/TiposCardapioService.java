package atos.tiposcardapios.service;

import atos.tiposcardapios.entity.TiposCardapioEntity;
import atos.tiposcardapios.repository.TiposCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiposCardapioService {

    @Autowired
    private TiposCardapioRepository tiposCardapioRepository;

    public List<TiposCardapioEntity> findAll() {
        return tiposCardapioRepository.findAll();
    }

    public TiposCardapioEntity findByName(String nome) {
        return tiposCardapioRepository.findByNome(nome);
    }


    public TiposCardapioEntity saveOrUpdateTipoCardapio(TiposCardapioEntity tipoCardapio) {

        TiposCardapioEntity existingTipo = tiposCardapioRepository.findByNome(tipoCardapio.getNome());
        if (existingTipo != null && !existingTipo.getId().equals(tipoCardapio.getId())) {
            throw new RuntimeException("Já existe um tipo de cardápio com o nome informado");
        }


        if (!tipoCardapio.getStatus().equals("A") && !tipoCardapio.getStatus().equals("I")) {
            throw new RuntimeException("O status deve ser 'A' para Ativo ou 'I' para Inativo");
        }


        if (tipoCardapio.getNome().length() < 3) {
            throw new RuntimeException("O nome do tipo deve possuir pelo menos três caracteres");
        }


        return tiposCardapioRepository.save(tipoCardapio);
    }

    public Page<TiposCardapioEntity> findByNomeContaining(String nome, Pageable pageable) {
        return tiposCardapioRepository.findByNomeContaining(nome, pageable);
    }

    public Optional<TiposCardapioEntity> findById(Integer id) {
        return tiposCardapioRepository.findById(id);
    }

    public void atualizarStatus(Integer id, String status) {

        if (id <= 0) {
            throw new RuntimeException("O código deve ser maior que zero");
        }

        TiposCardapioEntity tipoCardapio = tiposCardapioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe tipo vinculado ao código informado"));

        if (!status.equals("A") && !status.equals("I")) {
            throw new RuntimeException("O novo status deve ser 'A' para Ativo ou 'I' para Inativo");
        }

        tipoCardapio.setStatus(status);
        tiposCardapioRepository.save(tipoCardapio);
    }


}

package atos.tiposcardapios.controller;

import atos.tiposcardapios.entity.TiposCardapioEntity;
import atos.tiposcardapios.service.TiposCardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-cardapios")
public class TiposCardapioController {

    @Autowired
    private TiposCardapioService tiposCardapioService;

    @PostMapping
    public ResponseEntity<TiposCardapioEntity> salvarTipoCardapio(@RequestBody TiposCardapioEntity tipoCardapio) {
        TiposCardapioEntity savedTipoCardapio = tiposCardapioService.saveOrUpdateTipoCardapio(tipoCardapio);
        String location = "/tipos-cardapios/id/" + savedTipoCardapio.getId();
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", location).build();
    }

//    @GetMapping
//    public ResponseEntity<List<TiposCardapioEntity>> listarTiposCardapio(
//            @RequestParam(name = "nome") String nome,
//            @RequestParam(name = "pagina", defaultValue = "0") int pagina,
//            @RequestParam(name = "tamanho", defaultValue = "20") int tamanho
//    ) {
//        if (nome.length() < 3) {
//            throw new RuntimeException("O nome informado deve possuir no mínimo 3 caracteres");
//        }
//
//        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("nome").ascending());
//        Page<TiposCardapioEntity> tiposCardapioPage = tiposCardapioService.findByNomeContaining(nome, pageable);
//
//        if (pagina >= tiposCardapioPage.getTotalPages()) {
//            return ResponseEntity.ok(Collections.emptyList());
//        }
//        return ResponseEntity.ok(tiposCardapioPage.getContent());
//    }

    @GetMapping("/all")
    public ResponseEntity<List<TiposCardapioEntity>> listarTodosTiposCardapio() {
        List<TiposCardapioEntity> tiposCardapioList = tiposCardapioService.findAll();
        return ResponseEntity.ok(tiposCardapioList);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<TiposCardapioEntity> listarPorNomeTiposCardapio(@PathVariable String nome) {
        if (nome.length() < 3) {
            throw new RuntimeException("O nome informado deve possuir no mínimo 3 caracteres");
        }
        TiposCardapioEntity tiposCardapio = tiposCardapioService.findByName(nome);
        return ResponseEntity.ok(tiposCardapio);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<TiposCardapioEntity>> listarPorIdTiposCardapio(@PathVariable Integer id) {
        Optional<TiposCardapioEntity> tiposCardapio = tiposCardapioService.findById(id);
        return ResponseEntity.ok(tiposCardapio);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TiposCardapioEntity> atualizarTipoCardapio(@PathVariable("id") Integer id, @RequestBody TiposCardapioEntity tipoCardapio) {
        if (!id.equals(tipoCardapio.getId())) {
            throw new RuntimeException("O ID informado não corresponde ao ID do tipo de cardápio");
        }
        TiposCardapioEntity updatedTipoCardapio = tiposCardapioService.saveOrUpdateTipoCardapio(tipoCardapio);
        return ResponseEntity.ok(updatedTipoCardapio);
    }

    @PatchMapping("/id/{id}/status/{status}")
    public ResponseEntity<String> atualizarStatusTipoCardapio(
            @PathVariable("id") Integer id,
            @PathVariable("status") String status) {
        try {
            tiposCardapioService.atualizarStatus(id, status);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

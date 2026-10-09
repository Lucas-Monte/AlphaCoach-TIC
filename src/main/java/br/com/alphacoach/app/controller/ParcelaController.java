package br.com.alphacoach.app.controller;

import br.com.alphacoach.app.dto.request.ParcelaRequest;
import br.com.alphacoach.app.dto.response.ParcelaResponse;
import br.com.alphacoach.app.service.ParcelaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parcela")
public class ParcelaController {
    ParcelaService parcelaService;

    public ParcelaController(ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<ParcelaResponse> alterar(@RequestBody ParcelaRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(parcelaService.alterar(request, id));
    }

    @PatchMapping("/{id}/estornar")
    public ResponseEntity<ParcelaResponse> estornar(@PathVariable Long id) {
        ParcelaResponse estornada = parcelaService.estornar(id);
        return ResponseEntity.ok(estornada);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ParcelaResponse> cancelar(@PathVariable Long id) {
        ParcelaResponse estornada = parcelaService.cancelar(id);
        return ResponseEntity.ok(estornada);
    }

    @PatchMapping("/{id}/atualizar/status")
    public ResponseEntity<ParcelaResponse> atualizarStatus(@PathVariable Long id) {
        ParcelaResponse parcela = parcelaService.atualizarStatus(id);
        return ResponseEntity.ok(parcela);
    }
}

package br.com.alphacoach.app.dto.response;

import br.com.alphacoach.app.model.Planos;

public record PlanosResponse(Planos plano, Float valorMeses) {
}

package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.service.ITurnoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }
}

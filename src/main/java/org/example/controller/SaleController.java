package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.SaleRegisterRequestDTO;
import org.example.dto.SaleRegisterResponseDTO;
import org.example.manager.SaleManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
  private final SaleManager manager;

  @PostMapping("/register")
  public SaleRegisterResponseDTO register(@RequestBody SaleRegisterRequestDTO requestDTO) {
    return manager.register(requestDTO);
  }
}

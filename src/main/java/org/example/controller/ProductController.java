package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProductGetAllResponseDTO;
import org.example.dto.ProductGetByIdResponseDTO;
import org.example.dto.ProductSaveRequestDTO;
import org.example.dto.ProductSaveResponseDTO;
import org.example.manager.ProductManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductManager manager;

  @GetMapping("/getAll")
  public ProductGetAllResponseDTO getAll() {
    return manager.getAll();
  }

  @GetMapping("/getById")
  public ProductGetByIdResponseDTO getByIdFromParam(@RequestParam long id) {
    return manager.getById(id);
  }

  @GetMapping("/getById/{id}")
  public ProductGetByIdResponseDTO getByIdFromPath(@PathVariable long id) {
    return manager.getById(id);
  }

  @PostMapping("/save")
  public ProductSaveResponseDTO save(@RequestBody ProductSaveRequestDTO requestDTO) {
    return manager.save(requestDTO);
  }

  @PostMapping("/removeById")
  public void removeByIdFromParam(@RequestParam long id) {
    manager.removeById(id);
  }

  @PostMapping("/removeById/{id}")
  public void removeByIdFromPath(@PathVariable long id) {
    manager.removeById(id);
  }
}

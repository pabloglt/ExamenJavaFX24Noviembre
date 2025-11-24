package org.example.javafxx.MODELS;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Persona {
private String email;
private String plataforma;
private Boolean administrador;
private Double version;
private LocalDateTime fechaCreacion;
}

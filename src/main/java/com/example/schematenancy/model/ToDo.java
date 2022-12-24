package com.example.schematenancy.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {

    @Id
    private UUID toDoId;

    private String description;

    private Integer importance;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime startAt;

    private LocalDateTime endAt;


}

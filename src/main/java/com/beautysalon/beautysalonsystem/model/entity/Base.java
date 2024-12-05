package com.beautysalon.beautysalonsystem.model.entity;

import com.google.gson.Gson;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@MappedSuperclass
public class Base implements Serializable {


    @JsonbTransient
    private boolean editing = false;

    @JsonbTransient
    private boolean deleted = false;


}

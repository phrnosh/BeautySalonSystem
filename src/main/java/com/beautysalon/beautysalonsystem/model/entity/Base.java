package com.beautysalon.beautysalonsystem.model.entity;

import com.google.gson.Gson;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@MappedSuperclass
public class Base implements Serializable {

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "attach_id")
    private List<Attachment> attachmentList;

    @JsonbTransient
    private boolean editing = false;

    @JsonbTransient
    private boolean deleted = false;

    public void addAttachment(Attachment attachment) {
        if (this.attachmentList == null) {
            this.attachmentList = new ArrayList<>();
        }
        attachmentList.add(attachment);
    }

    @Override
    public String toString() {
        return  new Gson().toJson(this);
    }

}

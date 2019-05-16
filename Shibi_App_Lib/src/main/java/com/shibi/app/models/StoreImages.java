package com.shibi.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by User on 04/07/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="store_images")
public class StoreImages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long storeImagesId;

    @Column(name = "store_id")
    private Long productId;

    private String path;
}

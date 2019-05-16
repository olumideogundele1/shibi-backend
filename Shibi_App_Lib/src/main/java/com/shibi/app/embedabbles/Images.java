package com.shibi.app.embedabbles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shibi.app.utils.JsonDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 06/06/2018.
 */
@Data
@Embeddable
public class Images implements Serializable {

    @JsonIgnore
    @Column(name = "IMAGE_ID")
    private String imageId;

    @JsonIgnore
    @Lob
    @Column(name = "ACTUAL_IMAGE")
    private byte[] actualImage;

    @JsonIgnore
    @Lob
    @Column(name = "THUMBNAIL_IMG")
    private byte[] thumbnailImg;

    @Column(name = "FILE_NAME", length = 256)
    private String fileName;

    @JsonIgnore
    @Column(name = "PHOTO_HEIGHT")
    private Long photoHeight;

    @JsonIgnore
    @Column(name = "PHOTO_WIDTH")
    private Long photoWidth;

    @JsonIgnore
    @Column(name = "THUMB_HEIGHT")
    private Long thumbNailHeight;

    @JsonIgnore
    @Column(name = "THUMB_WIDTH")
    private Long thumbNailWidth;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @Column(name = "UPLOAD_DATE")
    private Date uploadDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @Column(name = "LAST_UPDATE")
    private Date lastUpdate;

    @Column(name = "CONTENT_TYPE", length = 128)
    private String contentType;

    @Column(name = "DOC_SIZE")
    private Double docSize;

    @JsonIgnore
    @Column(name = "Photo_DESC", length = 100)
    private String photoDesc;

    public Images() {
    }
}

//package com.shibi.app.models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
///**
// * Created by User on 04/07/2018.
// */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="files")
//public class FilesImage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long fileId;
//
//    @Column(name="file_name", length = 100)
//    private String fileName;
//
//    @Column(name = "file_directory", length=100)
//    private String fileDirectory;
//
//    @Column(name="file_extension", length=5)
//    private String fileExtension;
//
//    @Transient
//    private String fileBaseName;
//
//    @OneToOne(fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL,
//            mappedBy = "filesImage")
//    private Stores stores;
//
//    public FilesImage(
//            String fileDirectory, String fileName,
//            String fileExtension, String fileBaseName
//    ) {
//        this.fileDirectory = fileDirectory;
//        this.fileName = fileName;
//        this.fileExtension = fileExtension;
//        this.fileBaseName = fileBaseName;
//    }
//
//    public Path getFilePath() {
//        if(fileName == null || fileDirectory == null) {
//            return null;
//        }
//        return Paths.get(fileDirectory, fileName);
//    }
//}

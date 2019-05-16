package com.shibi.app.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 06/06/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailContent {

    private String from;
    private String[] to;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> model;
}

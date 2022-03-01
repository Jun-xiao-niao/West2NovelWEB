package com.junxiaoniao.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadingNovel {
    private int novelID;
    private String writerName;
    private String novelName;
    private String novelTag;
    private String brief;
    private String fileURL;

}

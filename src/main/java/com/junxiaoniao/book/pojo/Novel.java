package com.junxiaoniao.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Novel implements Comparable<Novel> {
    private int novelID;
    private String writerName;
    private String novelName;
    private String novelTag;
    private String brief;
    private int collection;
    private String fileURL;
    private String pictureURL;

    @Override
    public int compareTo(Novel o) {
        int i = this.collection - o.collection;
        return i;
    }
}


package com.junxiaoniao.book.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadingPicture {
    private int pictureID;
    private String pictureName;
    private String pictureURL;
}

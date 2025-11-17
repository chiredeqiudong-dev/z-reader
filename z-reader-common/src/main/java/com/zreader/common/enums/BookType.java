package com.zreader.common.enums;


import java.util.ArrayList;
import java.util.List;

/**
 * 书籍类型
 * 支持的文件类型
 *
 * @author zy
 * @date 2025/11/16
 */
public enum BookType {

    TXT("txt", "txt 后缀文件"),
    EPUB("epub", "epub 后缀文件"),
    PDF("pdf", "pdf 后缀文件"),
    MOBI("mobi", "mobi 后缀文件"),
    AZW("azw", "azw 后缀文件"),
    AZW3("azw3", "azw3 后缀文件");

    private final String type;
    private final String desc;


    BookType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 获取当前支持的所有类型
     * @return
     */
    public static List<String> getBookTypes() {
        List<String> bookTypes = new ArrayList<>();
        for (BookType bookType : BookType.values()) {
            bookTypes.add(bookType.getType());
        }
        return bookTypes;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}

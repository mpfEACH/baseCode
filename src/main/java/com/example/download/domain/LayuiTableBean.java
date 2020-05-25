package com.example.download.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Project jspdemo
 * @ClassName LayuiTableBean
 * @Author MPF
 * @Date 2020/5/25 0025 14:41
 * @Version 1.0
 * @Description TODO
 **/

@Data
public class LayuiTableBean implements Serializable{

    private String code;
    private String msg;
    private String count;
    private Object data;
}

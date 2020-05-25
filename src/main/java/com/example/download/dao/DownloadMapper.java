package com.example.download.dao;

import com.example.download.domain.Laagent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Project jspdemo
 * @ClassName DownloadDao
 * @Author MPF
 * @Date 2020/5/23 0023 20:31
 * @Version 1.0
 * @Description TODO
 **/

@Mapper
public interface DownloadMapper {

    /**
     * 根据关键词分页查询数据
     * @param keyWords
     * @return
     */
    public abstract List<Laagent> findDataByPage(@Param("keyWords") String keyWords);


    /**
     * 根据关键词分页查询数据总条数
     * @param keyWords
     * @return
     */
    public abstract Integer findDataCount(@Param("keyWords") String keyWords);

}

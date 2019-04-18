package com.nowcoder.dao;

import com.nowcoder.model.News;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 13:52 2019/3/28
 */
@Mapper
public interface NewsDao {

    String TABLE_NAME="news";
    String INSET_FIELDS="title,link,image,like_count,comment_count,created_date,user_id";
    String SELECT_FIELDS="id,"+INSET_FIELDS;

    @Insert({"insert into",TABLE_NAME,"(",INSET_FIELDS,") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    void addNews(News news);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{newsId}" })
    News getById(@Param("newsId") int newsId);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Update({"update",TABLE_NAME,"set comment_count=#{commentCount} where id=#{newsId}"})
    void updateCommentCount(@Param("newsId") int newsId,
                            @Param("commentCount") int commentCount);
}

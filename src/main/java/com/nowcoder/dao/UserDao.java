package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 13:52 2019/3/28
 */
@Mapper
public interface UserDao {
    String TABLE_NAME="user";
    String INSET_FIELDS="name,password,salt,head_url";
    String SELECT_FIELDS="id,name,password,salt,head_url";

    @Insert({"insert into",TABLE_NAME,"(",INSET_FIELDS,") values (#{Name},#{Password},#{Salt},#{HeadUrl})"})
    int addUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{Id}"})
    User selectById(int id);

    @Update({"update",TABLE_NAME,"set password=#{Password} where id=#{Id}"})
    void updatePassword(User user);


    @Delete({"delete from ",TABLE_NAME,"where id=#{Id}"})
    void deleteById(int id);
}

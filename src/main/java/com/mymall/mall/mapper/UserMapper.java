package com.mymall.mall.mapper;

import com.mymall.mall.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymall.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface UserMapper {

    int deleteById(Long userId);

    int insert(User user);

    int insertSelective(User user);

    User selectById(Long userId);

    User selectByLoginName(String loginName);

    User selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    int updateByIdSelective(User user);

    int updateById(User user);

    List<User> findUserList(PageQueryUtil pageUtil);

    int getTotalUsers(PageQueryUtil pageUtil);

    int lockUserBatch(@Param("ids") Long[] ids, @Param("lockStatus") int lockStatus);
}

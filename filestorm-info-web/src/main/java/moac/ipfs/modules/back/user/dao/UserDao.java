package moac.ipfs.modules.back.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import moac.ipfs.modules.back.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-09-13 10:45:37
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<UserEntity> {

    List<UserEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] userIds);

    /**
     * 根据用户地址查询用户id
     * @param address
     * @return
     */
    Long queryUserIdByAddress(String address);
	
}

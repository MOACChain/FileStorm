package moac.ipfs.modules.back.user.dao;

import moac.ipfs.modules.back.user.entity.UserAssetsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-21 15:52:21
 */
@Mapper
@Repository
public interface UserAssetsDao extends BaseMapper<UserAssetsEntity> {

    List<UserAssetsEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] assetsIds);
	
}

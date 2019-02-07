package moac.ipfs.modules.back.user.dao;

import moac.ipfs.modules.back.user.entity.KeyStoreEntity;
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
 * @date 2018-11-23 16:02:55
 */
@Mapper
@Repository
public interface KeyStoreDao extends BaseMapper<KeyStoreEntity> {

    List<KeyStoreEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] keyStoreIds);
	
}

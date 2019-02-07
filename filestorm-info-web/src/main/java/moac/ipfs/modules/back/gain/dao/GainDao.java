package moac.ipfs.modules.back.gain.dao;

import moac.ipfs.modules.back.gain.entity.GainEntity;
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
 * @date 2018-11-21 15:57:21
 */
@Mapper
@Repository
public interface GainDao extends BaseMapper<GainEntity> {

    List<GainEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] gainIds);
	
}

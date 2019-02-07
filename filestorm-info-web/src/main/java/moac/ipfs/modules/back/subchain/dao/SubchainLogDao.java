package moac.ipfs.modules.back.subchain.dao;

import moac.ipfs.modules.back.subchain.entity.SubchainLogEntity;
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
 * @date 2018-11-09 10:27:30
 */
@Mapper
@Repository
public interface SubchainLogDao extends BaseMapper<SubchainLogEntity> {

    List<SubchainLogEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Integer[] subLogIds);
	
}

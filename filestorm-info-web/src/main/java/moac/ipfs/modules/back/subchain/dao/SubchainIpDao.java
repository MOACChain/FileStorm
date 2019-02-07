package moac.ipfs.modules.back.subchain.dao;

import moac.ipfs.modules.back.subchain.entity.SubchainIpEntity;
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
 * @date 2018-11-19 09:30:41
 */
@Mapper
@Repository
public interface SubchainIpDao extends BaseMapper<SubchainIpEntity> {

    List<SubchainIpEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Integer[] ids);
	
}

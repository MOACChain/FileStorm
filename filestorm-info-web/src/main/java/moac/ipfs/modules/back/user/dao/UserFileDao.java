package moac.ipfs.modules.back.user.dao;

import moac.ipfs.modules.back.user.entity.UserFileEntity;
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
 * @date 2018-11-02 18:07:06
 */
@Mapper
@Repository
public interface UserFileDao extends BaseMapper<UserFileEntity> {

    List<UserFileEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] fileIds);
	
}

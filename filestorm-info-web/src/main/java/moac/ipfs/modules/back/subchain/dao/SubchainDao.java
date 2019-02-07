package moac.ipfs.modules.back.subchain.dao;

import moac.ipfs.modules.back.subchain.entity.SubchainEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * @author GZC
 * @email 57855143@qq.com
 * @date 2018-11-02 18:10:35
 */
@Mapper
@Repository
public interface SubchainDao extends BaseMapper<SubchainEntity> {

    List<SubchainEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void deleteBatch(Long[] subchainIds);

    /**
     * 查询符合新增文件条件的子链合约地址
     * @param map
     * @return
     */
    SubchainEntity queryAddFileList(Map<String, Object> map);

    /**
     * 查询缓存存储子链
     * @return
     */
    List<String> querySubList();

    /**
     * 新增缓存存储子链
     * @return
     */
    void insertSubList(String subchainAddress);

    /**
     * 删除缓存存储子链
     * @return
     */
    void deleteSubList(String subchainAddress);
}
